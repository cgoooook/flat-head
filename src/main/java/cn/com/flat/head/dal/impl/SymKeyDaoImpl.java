package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.SymKeyDao;
import cn.com.flat.head.dal.mappers.SymKeyMapper;
import cn.com.flat.head.pojo.SymKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SymKeyDaoImpl implements SymKeyDao {

    @Autowired
    private SymKeyMapper keyMapper;

    @Override
    public int addKey(SymKey key) {
        return keyMapper.addKey(key);
    }

    @Override
    public boolean updateKey(SymKey key) {
        SymKey keyById = getKeyById(key.getKeyId());
        key.setVersion(keyById.getVersion() + 1);
        int updateKeyNum = keyMapper.updateKey(key);
        /*
        KeyHistory keyHistory = convertKeyToHistory(keyById);
        int ret = keyHistoryMapper.adHistoryKey(keyHistory);
        return (updateKeyNum + ret > 1);
        */
        return updateKeyNum==1;
    }

    @Override
    public int updateKeyStatus(String keyId, int status) {
        return keyMapper.updateKeyStatus(keyId, status);
    }

    @Override
    public void deleteKeyById(String id) {
        keyMapper.deleteKeyById(id);
    }

    @Override
    public SymKey getKeyById(String keyId) {
        return keyMapper.getKeyById(keyId);
    }
    @Override
    public int getKeyCount()
    {
        return keyMapper.getKeyCount();
    }

    @Override
    public List<SymKey> getKeyListPage() {
        return keyMapper.getKeyListPage();
    }
}
