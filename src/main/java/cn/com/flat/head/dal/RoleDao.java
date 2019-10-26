package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Role;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/26.
 */
public interface RoleDao {

    List<Role> getRoleListForUser();

}
