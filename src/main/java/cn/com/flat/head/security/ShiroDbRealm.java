package cn.com.flat.head.security;

import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * Created by poney on 2019-10-05.
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) getAvailablePrincipal(principalCollection);
        if (StringUtils.isNotBlank(username)) {
            List<String> roles = userService.getUserRolesByUsername(username);
            List<String> permTokens = userService.getUserTokensByUsername(username);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if (roles != null) {
                info.addRoles(roles);
            }
            if (permTokens != null) {
                info.addStringPermissions(permTokens);
            }
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        if (StringUtils.isNotBlank(username)) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            } else {
                throw new AuthenticationException("user don't match");
            }
        }
        return null;
    }
}
