package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Role;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/26.
 */
public interface RoleService {

    List<Role> getRoleListForUser();

    List<Role> getRoleListPage(Pageable pageable, Role role);

    BooleanCarrier addRole(Role role);

    Role getRoleById(String roleId);

    BooleanCarrier updateRole(Role role);

    BooleanCarrier deleteRoleById(String roleId);

}
