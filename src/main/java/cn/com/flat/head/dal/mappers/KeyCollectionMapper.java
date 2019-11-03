package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.KeyCollection;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
@RepositoryImpl
public interface KeyCollectionMapper {

    List<KeyCollection> getKeyCollectionListPage(KeyCollection collection);

    KeyCollection getKeyCollectionById(String connectionId);

    int updateCollection(KeyCollection connection);

    List<KeyCollection> getCollectionByOrgId(String orgId);

    int addCollection(KeyCollection collection);

    int deleteKeyCollection(String collectionId);

}
