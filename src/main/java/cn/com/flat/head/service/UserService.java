package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.User;

import java.util.List;

/**
 * Created by poney on 2019-09-30.
 */
public interface UserService {

    User getUserByUsername(String username);

    List<String> getUserRolesByUsername(String username);

    List<String> getUserTokensByUsername(String username);

    List<User> getUserListPage(User user, Pageable pageable);
}
