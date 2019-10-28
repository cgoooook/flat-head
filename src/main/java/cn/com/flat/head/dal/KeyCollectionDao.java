package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.KeyCollection;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
public interface KeyCollectionDao {

    List<KeyCollection> getCollectionListPage(String orgId);

    KeyCollection getCollectionById(String collectionId);

    List<KeyCollection> getCollectionByOrgId(String orgId);

    int addCollection(KeyCollection collection);

    int updateCollection(KeyCollection collection);

    int deleteCollection(String collectionId);

    boolean addCollectionKeys(String collectionId, List<String> keys);

}
