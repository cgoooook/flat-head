package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Key;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/29.
 */
@RepositoryImpl
public interface KeyMapper {

    int addKey(Key key);

    List<Key> getKeyListPage(Key key);

    int updateKey(Key key);

    int getRootKeyCount();

    int updateKeyStatus(@Param("keyId") String keyId, @Param("status") int status);
}
