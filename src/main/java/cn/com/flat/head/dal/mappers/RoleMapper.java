package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Role;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
@RepositoryImpl
public interface RoleMapper {

    List<Role> getRoleListForUser();

    List<Role> getRoleListPage(Role role);

    int getRoleCountByRoleName(String roleName);

    int addRole(Role role);

    int addRolePermission(Role role);

    Role getRoleById(String roleId);

    List<String> getRolePermToken(String roleId);

    int updateRole(Role role);

    int deleteRolePermToeken(String roleId);

    int deleteRoleById(String roleId);

    int getRoleUsersByRoleId(String roleId);


}
