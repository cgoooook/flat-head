package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.SymKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RepositoryImpl
public interface SymKeyMapper {

    int addKey(SymKey key);
    int updateKey(SymKey key);
    int updateKeyStatus(@Param("keyId") String keyId, @Param("status") int status);
    void deleteKeyById(String id);
    SymKey getKeyById(String keyId);
    int getKeyCount();

    List<SymKey> getKeyListPage();
}
