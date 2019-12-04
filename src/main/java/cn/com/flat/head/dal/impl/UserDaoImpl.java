package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.UserDao;
import cn.com.flat.head.dal.mappers.UserMapper;
import cn.com.flat.head.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by poney on 2019-09-30.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public List<String> getUserRoleByUsername(String username) {
        return userMapper.getUserRolesByUsername(username);
    }

    @Override
    public List<String> getUserTokensByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        return userMapper.getUserTokensByUserId(user.getUserId());
    }

    @Override
    public List<User> getUserListPage(User user) {
        return userMapper.getUserListPage(user);
    }

    @Override
    public boolean deleteUserById(String userId) {
        return userMapper.deleteUserById(userId) >= 1;
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public int addUserRole(User user) {
        return userMapper.addUserRole(user);
    }

    @Override
    public int updateUserRole(String userId, String roleId) {
        return userMapper.updateUserRole(userId, roleId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
