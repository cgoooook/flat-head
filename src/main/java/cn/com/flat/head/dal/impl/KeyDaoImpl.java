package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.dal.mappers.KeyHistoryMapper;
import cn.com.flat.head.dal.mappers.KeyMapper;
import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.pojo.KeyHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by panzhuowen on 2019/11/6.
 */
@Repository
public class KeyDaoImpl implements KeyDao {

    @Autowired
    private KeyMapper keyMapper;

    @Autowired
    private KeyHistoryMapper keyHistoryMapper;

    @Override
    public List<Key> getKeyListPage(Key key) {
        return keyMapper.getKeyListPage(key);
    }

    @Override
    public int getRootKeyCount() {
        return keyMapper.getRootKeyCount();
    }

    @Override
    public int addKey(Key key) {
        key.setKeyId(UUID.randomUUID().toString());
        key.setStatus(2);
        return keyMapper.addKey(key);
    }

    @Override
    public int updateKeyStatus(String keyId, int status) {
        return keyMapper.updateKeyStatus(keyId, status);
    }

    @Override
    public Key getKeyById(String keyId) {
        return keyMapper.getKeyById(keyId);
    }

    @Override
    public List<KeyHistory> getKeyHistory(String keyId) {
        return keyHistoryMapper.getKeyHistory(keyId);
    }
}
