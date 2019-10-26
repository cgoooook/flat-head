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

    boolean deleteUserById(int userId);
}
