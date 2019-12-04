package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;

/**
 * Created by poney on 2019-10-04.
 */
@RepositoryImpl
public interface PermissionMapper {

    String getPermTokenIdByToken(String token);

}
