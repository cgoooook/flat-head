package cn.com.flat.head.service.impl;

import cn.com.flat.head.crypto.CryptoInstance;
import cn.com.flat.head.crypto.FSecretKey;
import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.dal.KeyTemplateDao;
import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.KeyService;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
@Service
public class KeyServiceImpl implements KeyService {

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private OrgDao orgDao;


    @Autowired
    private KeyTemplateDao keyTemplateDao;

    private CryptoInstance cryptoInstance = CryptoInstance.getCryptoInstance();

    @Override
    public List<Key> getKeyListPage(Pageable pageable, Key key) {
        PageableInterceptor.startPage(pageable);
        return keyDao.getKeyListPage(key);
    }

    @Override
    public List<Organization> getKeyGenOrg() {
        int rootKeyCount = keyDao.getRootKeyCount();
        if (rootKeyCount > 0) {
            return orgDao.orgChildList();
        } else {
            Organization orgRoot = orgDao.getOrgByOrgId("orgRoot");
            List<Organization> organizations = new ArrayList<>();
            organizations.add(orgRoot);
            return organizations;
        }
    }

    @Override
    public List<KeyTemplate> getKeyGenTemplate() {
        return keyTemplateDao.getTemplateUse();
    }

    @Override
    public boolean checkRootKey() {
        return !(keyDao.getRootKeyCount() > 0);
    }

    @Override
    public BooleanCarrier addKey(Key key) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            FSecretKey fSecretKey = null;
            if ("random".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.generateKey(key.getKeyAlg(), key.getLength());
            } else if ("derive".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.deriveKey(key, key.getDeriveParam());
            } else if ("compose".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.composeKey(key.getComposes(), key.getKeyAlg());
            } else {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("");
                return booleanCarrier;
            }
            key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
            key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));
            int i = keyDao.addKey(key);
            booleanCarrier.setResult(i >= 1);
            return booleanCarrier;
        } catch (Exception e) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("");
            return booleanCarrier;
        }
    }

    @Override
    public boolean updateKeyStatus(String keyId, int status) {
        return keyDao.updateKeyStatus(keyId, status) >= 1;
    }

    @Override
    public Key getKeyById(String keyId) {
        return keyDao.getKeyById(keyId);
    }

    @Override
    public List<KeyHistory> getKeyHistory(String keyId) {
        return keyDao.getKeyHistory(keyId);
    }

    @Override
    public BooleanCarrier updateKey(Key key) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            FSecretKey fSecretKey = null;
            if ("random".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.generateKey(key.getKeyAlg(), key.getLength());
            } else if ("derive".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.deriveKey(key, key.getDeriveParam());
            } else if ("compose".equalsIgnoreCase(key.getMode())) {
                fSecretKey = cryptoInstance.composeKey(key.getComposes(), key.getKeyAlg());
            } else {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("");
                return booleanCarrier;
            }
            key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
            key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));
            boolean ret = keyDao.updateKey(key);
            booleanCarrier.setResult(ret);
            return booleanCarrier;
        } catch (Exception e) {
            e.printStackTrace();
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("");
            return booleanCarrier;
        }
    }
}
