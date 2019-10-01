package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.pojo.User;
import cn.com.flat.head.mybatis.RepositoryImpl;

/**
 * Created by poney on 2019-09-30.
 */
@RepositoryImpl
public interface UserMapper {
    
    User getUserByUsername(String username);
    
}
