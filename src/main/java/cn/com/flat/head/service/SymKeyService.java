package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.SymKey;
import cn.com.flat.head.pojo.BooleanCarrier;

import java.util.List;

public interface SymKeyService {

    List<SymKey> getKeyListPage(Pageable pageable, SymKey key);

    SymKey apply(String alg, int bits) throws Exception;
    SymKey fetch(String keyId);
    SymKey update(SymKey key) throws Exception;
    BooleanCarrier revoke(String keyId, String reason);

    long count(String alg, int bits);

    boolean updateStatus(String keyId, int status);
}
