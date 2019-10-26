package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Role;

import java.util.List;

/**
 * Created by poney on 2019-10-04.
 */
@RepositoryImpl
public interface RoleMapper {

    List<Role> getRoleListForUser();

}
