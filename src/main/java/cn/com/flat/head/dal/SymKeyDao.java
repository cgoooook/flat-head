package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.SymKey;

import java.util.List;

public interface SymKeyDao {
    int addKey(SymKey key);
    boolean updateKey(SymKey key);
    int updateKeyStatus(String keyId, int status);
    void deleteKeyById(String id);
    SymKey getKeyById(String keyId);
    int getKeyCount();

    List<SymKey> getKeyListPage();
}
