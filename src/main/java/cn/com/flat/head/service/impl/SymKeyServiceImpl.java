package cn.com.flat.head.service.impl;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.SymKey;
import cn.com.flat.head.service.SymKeyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymKeyServiceImpl implements SymKeyService {

    @Override
    public List<SymKey> getKeyListPage(Pageable pageable, SymKey key) {
        return null;
    }
    @Override
    public
    SymKey apply(String alg, int bits) {
        return null;
    }
    @Override
    public
    SymKey fetch(String keyId) {
        return null;
    }
    @Override
    public
    SymKey update(SymKey key) {
        return null;
    }
    @Override
    public
    BooleanCarrier revoke(String keyId, String reason) {
        return null;
    }
    @Override
    public
    long count(String alg, int bits) {
        return 0;
    }
    @Override
    public
    boolean updateStatus(String keyId, int status) {
        return false;
    }
}
