package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.pojo.User;
import cn.com.flat.head.mybatis.RepositoryImpl;
import org.apache.ibatis.annotations.Param;

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

    int deleteUserById(String userId);

    int deleteUserRole(String userId);

    int addUser(User user);

    User getUserById(String userId);

    int addUserRole(User user);

    int updateUserRole(@Param("userId") String userId, @Param("roleId") int roleId);

    int updateUser(User user);
}
