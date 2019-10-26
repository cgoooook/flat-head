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
}
