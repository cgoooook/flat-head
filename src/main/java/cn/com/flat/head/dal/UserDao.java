package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.User;

import java.util.List;

/**
 * Created by poney on 2019-09-30.
 */
public interface UserDao {

    User getUserByUsername(String username);

    List<String> getUserRoleByUsername(String username);

    List<String> getUserTokensByUsername(String username);

    List<User> getUserListPage(User user);

    boolean deleteUserById(String userId);

    int addUser(User user);

    User getUserById(String userId);

    int addUserRole(User user);

    int updateUserRole(String userId, int roleId);

    int updateUser(User user);
}
