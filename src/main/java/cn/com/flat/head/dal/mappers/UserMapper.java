package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.pojo.User;
import cn.com.flat.head.mybatis.RepositoryImpl;

import java.util.List;

/**
 * Created by poney on 2019-09-30.
 */
@RepositoryImpl
public interface UserMapper {
    
    User getUserByUsername(String username);

    List<String> getUserRolesByUsername(String username);

    List<String> getUserTokensByUserId(String userId);

    List<User> getUserListPage(User user);

    int deleteUserById(int userId);

    int deleteUserRole(int userId);

    User addUser(User user);

    User getUserById(String userId);
}
