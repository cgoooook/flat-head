package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.UserDao;
import cn.com.flat.head.dal.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by poney on 2019-09-30.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

}
