package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.PermissionDao;
import cn.com.flat.head.dal.RoleDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Role;
import cn.com.flat.head.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by panzhuowen on 2019/10/26.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Role> getRoleListForUser() {
        return roleDao.getRoleListForUser();
    }

    @Override
    public List<Role> getRoleListPage(Pageable pageable, Role role) {
        PageableInterceptor.startPage(pageable);
        return roleDao.getRoleListPage(role);
    }

    @Override
    public BooleanCarrier addRole(Role role) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            int roleCountByRoleName = roleDao.getRoleCountByRoleName(role.getRoleName());
            if (roleCountByRoleName > 0) {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("role.roleNameExits");
            } else {
                role.setRoleId(UUID.randomUUID().toString());
                roleDao.addRole(role);
                List<String> permTokens = role.getPermTokens();
                List<String> permIds = new ArrayList<>();
                permTokens.forEach(token -> {
                    String permTokenIdByPermToken = permissionDao.getPermTokenIdByPermToken(token);
                    permIds.add(permTokenIdByPermToken);
                });
                role.setPermIds(permIds);
                roleDao.addRolePermission(role);
                booleanCarrier.setResult(true);
            }

        } catch (Exception e) {
            booleanCarrier.setResult(false);
        }
        return booleanCarrier;
    }

    @Override
    public Role getRoleById(String roleId) {
        Role roleById = roleDao.getRoleById(roleId);
        List<String> strings = roleDao.gerRolePermToken(roleId);
        roleById.setPermTokens(strings);
        return roleById;
    }

    @Override
    public BooleanCarrier updateRole(Role role) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            Role roleById = roleDao.getRoleById(role.getRoleId());
            if (!roleById.getRoleName().equalsIgnoreCase(role.getRoleName())) {
                int roleCountByRoleName = roleDao.getRoleCountByRoleName(role.getRoleName());
                if (roleCountByRoleName > 0) {
                    booleanCarrier.setResult(false);
                    booleanCarrier.setMessage("role.roleNameExits");
                    return booleanCarrier;
                }
            }
            roleDao.updateRole(role);
            roleDao.deleteRolePermToken(role.getRoleId());
            List<String> permTokens = role.getPermTokens();
            List<String> permIds = new ArrayList<>();
            permTokens.forEach(token -> {
                String permTokenIdByPermToken = permissionDao.getPermTokenIdByPermToken(token);
                permIds.add(permTokenIdByPermToken);
            });
            role.setPermIds(permIds);
            roleDao.addRolePermission(role);
            booleanCarrier.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            booleanCarrier.setResult(false);
        }
        return booleanCarrier;
    }

    @Override
    public BooleanCarrier deleteRoleById(String roleId) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        int roleUsersByRoleId = roleDao.getRoleUsersByRoleId(roleId);
        if (roleUsersByRoleId > 0) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("role.roleUsages");
        } else {
            roleDao.deleteRoleById(roleId);
            booleanCarrier.setResult(true);
        }
        return booleanCarrier;
    }
}
