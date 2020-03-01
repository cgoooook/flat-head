package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.pojo.KeyHistory;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
public interface KeyDao {

    List<Key> getKeyListPage(Key key);

    int getRootKeyCount();

    int addKey(Key key);

    int updateKeyStatus(String keyId, int status);

    Key getKeyById(String keyId);

    List<KeyHistory> getKeyHistory(String keyId);

    boolean updateKey(Key key);

    Key getRootKey();

    Key getKeyByName(String name);

    Key getKeyByName(String name, String orgId);

    KeyHistory getKeyHistoryVersion(String keyId, String version);

    List<Key> getKeyListByOrgId(String orgId, String collectionId);

    List<Key> getKeyListByOrgIdNotEmpty(String orgId, String collectionId);

    List<Key> getKeyListByCollectionId(String collection);

    List<Key> getKeyByOrgId(String orgId);

    List<Key> getKeyByOrgIdAndCollectionId(String orgId, String collectionId);

    int geyKeyCountByTemplateId(String templateId);

    void deleteKeyById(String id);

    void deleteKeyHistoryById(String id);

    void addKeyHistorey(KeyHistory keyHistory);
}
