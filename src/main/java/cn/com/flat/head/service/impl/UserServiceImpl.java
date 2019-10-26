package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.UserDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by poney on 2019-09-30.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<String> getUserRolesByUsername(String username) {
        return userDao.getUserRoleByUsername(username);
    }

    @Override
    public List<String> getUserTokensByUsername(String username) {
        return userDao.getUserTokensByUsername(username);
    }

    @Override
    public List<User> getUserListPage(User user, Pageable pageable) {
        PageableInterceptor.startPage(pageable);
        return userDao.getUserListPage(user);
    }

    @Override
    public boolean deleteUserById(int userId) {

        return true;
    }
}
