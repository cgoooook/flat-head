package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.UserDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public boolean deleteUserById(String userId) {
        userDao.deleteUserById(userId);
        return true;
    }

    @Override
    public BooleanCarrier addUser(User user) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        User userByUsername = userDao.getUserByUsername(user.getUsername());
        if (userByUsername != null) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("user.usernameExits");
        } else {
            user.setUserId(UUID.randomUUID().toString());
            userDao.addUser(user);
            userDao.addUserRole(user);
            booleanCarrier.setResult(true);
        }

        return booleanCarrier;
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public BooleanCarrier updateUser(User user) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        User userById = userDao.getUserById(user.getUserId());
        if (!userById.getUsername().equals(user.getUsername())) {
            User userByUsername = userDao.getUserByUsername(user.getUsername());
            if (userByUsername != null) {
                booleanCarrier.setMessage("user.usernameExits");
                booleanCarrier.setResult(false);
            }
        } else {
            if (userById.getRoleId() != user.getRoleId()) {
                userDao.updateUserRole(user.getUserId(), user.getRoleId());
            }

            userDao.updateUser(user);
            booleanCarrier.setResult(true);
        }
        return booleanCarrier;
    }

    @Override
    public int updateUserLastLoginTime(String username) {
        return userDao.updateUserLastLoginTime(username);
    }
}
