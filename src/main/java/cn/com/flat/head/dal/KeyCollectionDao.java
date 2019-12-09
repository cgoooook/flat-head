package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.KeyCollection;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
public interface KeyCollectionDao {

    List<KeyCollection> getCollectionListPage(KeyCollection collection);

    KeyCollection getCollectionById(String collectionId);

    List<KeyCollection> getCollectionByOrgId(String orgId);

    int addCollection(KeyCollection collection);

    int updateCollection(KeyCollection collection);

    int deleteCollection(String collectionId);

    boolean addCollectionKeys(String collectionId, List<String> keys);

    int getCollectionByCollectionName(String name);

    int deleteCollectionKeys(String collectionId);

    int delSubKey(String collectionId, String keyId);

    int addSubKeys(List<String> keyIds, String collectionId);

    int getCollectionKeyNum(String collectionId);

    List<String> getCollectionKeys(String collectionId);

    KeyCollection getCollectionByName(String name);

}
