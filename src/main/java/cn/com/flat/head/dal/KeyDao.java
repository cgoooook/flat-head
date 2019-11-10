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

}