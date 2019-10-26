package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.RoleDao;
import cn.com.flat.head.pojo.Role;
import cn.com.flat.head.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/26.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getRoleListForUser() {
        return roleDao.getRoleListForUser();
    }
}
