package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Role;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/26.
 */
public interface RoleDao {

    List<Role> getRoleListForUser();

    List<Role> getRoleListPage(Role role);

    int getRoleCountByRoleName(String roleName);

    boolean addRole(Role role);

    boolean addRolePermission(Role role);

    Role getRoleById(String roleId);

    List<String> gerRolePermToken(String roleId);

    int updateRole(Role role);

    boolean deleteRolePermToken(String roleId);

    boolean deleteRoleById(String roleId);

    int getRoleUsersByRoleId(String roleId);

}
