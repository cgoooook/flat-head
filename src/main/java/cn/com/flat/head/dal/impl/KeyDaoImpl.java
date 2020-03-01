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

    @Override
    public Key getRootKey() {
        return keyMapper.getRootKey();
    }

    @Override
    public Key getKeyByName(String name) {
        return keyMapper.getKeyByName(name);
    }

    @Override
    public Key getKeyByName(String name, String orgId) {
        return keyMapper.getKeyByNameAndOrgId(name, orgId);
    }

    @Override
    public KeyHistory getKeyHistoryVersion(String keyId, String version) {
        return keyHistoryMapper.getHisoryKeyByVersion(keyId, version);
    }

    @Override
    public List<Key> getKeyListByOrgId(String orgId, String collectionId) {
        return keyMapper.getKeyListByOrgId(orgId, collectionId);
    }

    @Override
    public List<Key> getKeyListByOrgIdNotEmpty(String orgId, String collectionId) {
        return keyMapper.getKeyListByOrgIdNotEmpty(orgId, collectionId);
    }

    @Override
    public List<Key> getKeyListByCollectionId(String collection) {
        return keyMapper.getKeyListByCollectionId(collection);
    }

    @Override
    public List<Key> getKeyByOrgId(String orgId) {
        return keyMapper.getKeyListByOrgIdExport(orgId);
    }

    @Override
    public List<Key> getKeyByOrgIdAndCollectionId(String orgId, String collectionId) {
        return keyMapper.getKeyListByOrgIdAndCollectionId(orgId, collectionId);
    }

    @Override
    public int geyKeyCountByTemplateId(String templateId) {
        return keyMapper.getKeyCountByTemplateId(templateId);
    }

    @Override
    public void deleteKeyById(String id) {
        keyMapper.deleteKeyById(id);
    }

    @Override
    public void deleteKeyHistoryById(String id) {
        keyHistoryMapper.delKeyHistoryByKeyId(id);
    }

    @Override
    public void addKeyHistorey(KeyHistory keyHistory) {
        keyHistoryMapper.adHistoryKey(keyHistory);
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
