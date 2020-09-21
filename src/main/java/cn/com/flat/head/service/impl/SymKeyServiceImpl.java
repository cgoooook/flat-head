package cn.com.flat.head.service.impl;

import cn.com.flat.head.crypto.FSecretKey;
import cn.com.flat.head.dal.SymKeyDao;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.SymKey;
import cn.com.flat.head.service.KeyGenService;
import cn.com.flat.head.service.SymKeyService;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymKeyServiceImpl implements SymKeyService {

    @Autowired
    private SymKeyDao keyDao;

    @Autowired
    private KeyGenService keyGenService;

    @Override
    public List<SymKey> getKeyListPage(Pageable pageable, SymKey key) {
        return null;
    }

    @Override
    public
    SymKey apply(String alg, int bits) throws Exception {
        SymKey key = new SymKey();

        key.setKeyAlg(alg);
        key.setLength(bits);
        key.setVersion(0);
        key.setStatus(0);

        key.setCreateBy("API");
        FSecretKey fSecretKey = keyGenService.generateRandomKey(alg, bits);
        key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
        key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));

        keyDao.addKey(key);
        return key;
    }

    @Override
    public
    SymKey fetch(String keyId) {
        return keyDao.getKeyById(keyId);
    }

    @Override
    public
    SymKey update(SymKey key) throws Exception{
        FSecretKey fSecretKey = keyGenService.generateRandomKey(key.getKeyAlg(), key.getLength());
        key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
        key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));
        keyDao.updateKey(key);
        return key;
    }

    @Override
    public
    BooleanCarrier revoke(String keyId, String reason) {
        keyDao.deleteKeyById(keyId);
        SymKey key = new SymKey();
        BooleanCarrier carrier = new BooleanCarrier();
        carrier.setCode(0);
        carrier.setResult(true);
        carrier.setMessage("success");
        return carrier;
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
