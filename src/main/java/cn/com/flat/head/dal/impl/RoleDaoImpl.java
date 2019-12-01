package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.RoleDao;
import cn.com.flat.head.dal.mappers.RoleMapper;
import cn.com.flat.head.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/26.
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleListForUser() {
        return roleMapper.getRoleListForUser();
    }

    @Override
    public List<Role> getRoleListPage(Role role) {
        return roleMapper.getRoleListPage(role);
    }

    @Override
    public int getRoleCountByRoleName(String roleName) {
        return roleMapper.getRoleCountByRoleName(roleName);
    }

    @Override
    public boolean addRole(Role role) {
        roleMapper.addRole(role);
        return true;
    }

    @Override
    public boolean addRolePermission(Role role) {
        roleMapper.addRolePermission(role);
        return true;
    }

    @Override
    public Role getRoleById(String roleId) {
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public List<String> gerRolePermToken(String roleId) {
        return roleMapper.getRolePermToken(roleId);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public boolean deleteRolePermToken(String roleId) {
        return roleMapper.deleteRolePermToeken(roleId) >= 0;
    }

    @Override
    public boolean deleteRoleById(String roleId) {
         roleMapper.deleteRoleById(roleId);
         roleMapper.deleteRolePermToeken(roleId);
         return true;
    }

    @Override
    public int getRoleUsersByRoleId(String roleId) {
        return roleMapper.getRoleUsersByRoleId(roleId);
    }
}
