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

    @Override
    public boolean updateKey(Key key) {
        Key keyById = getKeyById(key.getKeyId());
        key.setVersion(keyById.getVersion() + 1);
        int updateKeyNum = keyMapper.updateKey(key);
        KeyHistory keyHistory = convertKeyToHistory(keyById);
        int ret = keyHistoryMapper.adHistoryKey(keyHistory);
        return (updateKeyNum + ret > 1);
    }

    private KeyHistory convertKeyToHistory(Key key) {
        KeyHistory keyHistory = new KeyHistory();
        keyHistory.setKeyHistoryId(UUID.randomUUID().toString());
        keyHistory.setKeyId(key.getKeyId());
        keyHistory.setKeyName(key.getKeyName());
        keyHistory.setKeyAlg(key.getKeyAlg());
        keyHistory.setLength(key.getLength());
        keyHistory.setKeyValue(key.getKeyValue());
        keyHistory.setCheckValue(key.getCheckValue());
        keyHistory.setVersion(key.getVersion());
        key.setCreateBy(key.getCreateBy());
        keyHistory.setOrgId(key.getOrgId());
        keyHistory.setTemplateId(key.getTemplateId());
        keyHistory.setStatus(key.getStatus());
        keyHistory.setMode(key.getMode());
        return keyHistory;
    }
}
