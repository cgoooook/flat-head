package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.PermissionDao;
import cn.com.flat.head.dal.mappers.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by panzhuowen on 2019/12/1.
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public String getPermTokenIdByPermToken(String permToken) {
        return permissionMapper.getPermTokenIdByToken(permToken);
    }
}
