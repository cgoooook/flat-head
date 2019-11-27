package cn.com.flat.head.service.impl;

import cn.com.flat.head.crypto.CryptoInstance;
import cn.com.flat.head.crypto.FSecretKey;
import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.dal.KeyTemplateDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.dal.OrgDao;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.KeyGenService;
import cn.com.flat.head.service.KeyService;
import org.apache.shiro.codec.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
@Service
public class KeyServiceImpl implements KeyService {

    private static Logger logger = LoggerFactory.getLogger(KeyServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Autowired
    private KeyDao keyDao;

    @Autowired
    private OrgDao orgDao;



    @Autowired
    private KeyGenService keyGenService;

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
        boolean result = true;
        try {
            FSecretKey fSecretKey;
            if ("random".equalsIgnoreCase(key.getMode())) {
                fSecretKey = keyGenService.generateRandomKey(key.getKeyAlg(), key.getLength());
            } else if ("derive".equalsIgnoreCase(key.getMode())) {
                Key rootKey = keyDao.getRootKey();
                fSecretKey = keyGenService.deriveKey(rootKey, key.getDeriveParam());
            } else if ("compose".equalsIgnoreCase(key.getMode())) {
                fSecretKey = keyGenService.composeKey(key.getComposes(), key.getKeyAlg());
            } else {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("key.noMode");
                return booleanCarrier;
            }
            key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
            key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));
            int i = keyDao.addKey(key);
            booleanCarrier.setResult(i >= 1);
            return booleanCarrier;
        } catch (Exception e) {
            logger.error("add key error", e);
            result = false;
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("key.genKeyError");
            return booleanCarrier;
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addKey, result, "add key key key name is:" + key.getKeyName()));
        }
    }

    @Override
    public boolean updateKeyStatus(String keyId, int status) {
        boolean result = true;
        try {
            result = keyDao.updateKeyStatus(keyId, status) >= 1;
        } catch (Exception e) {
            logger.error("update key status error", e);
            result = false;
        } finally {
            String opType = "";
            switch (status) {
                case 2:
                    opType = OperateType.enableKey;
                    break;
                case 3:
                    opType = OperateType.disableKey;
                    break;
                case 4:
                    opType = OperateType.archiveKey;
                    break;
                case 5:
                    opType = OperateType.deleteKey;
                    break;
            }
            logDao.addLog(LoggerBuilder.builder(opType, result, "update key status"));
        }

        return result;

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
        boolean result = true;
        try {
            Key keyById = keyDao.getKeyById(key.getKeyId());
            key.setKeyAlg(keyById.getKeyAlg());
            key.setLength(keyById.getLength());
            FSecretKey fSecretKey = null;
            if ("random".equalsIgnoreCase(key.getMode())) {
                fSecretKey = keyGenService.generateRandomKey(key.getKeyAlg(), key.getLength());
            } else if ("derive".equalsIgnoreCase(key.getMode())) {
                Key rootKey = keyDao.getRootKey();
                fSecretKey = keyGenService.deriveKey(rootKey, key.getDeriveParam());
            } else if ("compose".equalsIgnoreCase(key.getMode())) {
                fSecretKey = keyGenService.composeKey(key.getComposes(), key.getKeyAlg());
            } else {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("key.noMode");
                return booleanCarrier;
            }
            key.setCheckValue(new String(Hex.encode(fSecretKey.getCheckValue())));
            key.setKeyValue(new String(Hex.encode(fSecretKey.getKey())));
            boolean ret = keyDao.updateKey(key);
            booleanCarrier.setResult(ret);
            return booleanCarrier;
        } catch (Exception e) {
            logger.error("update key error", e);
            result = false;
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("key.genKeyError");
            return booleanCarrier;
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateKey, result, "updateKey keyname is" + key.getKeyName()));
        }
    }

    @Override
    public List<Key> getKeyListByOrgId(String orgId, String collectionId) {
        return keyDao.getKeyListByOrgIdNotEmpty(orgId, collectionId);
    }

    @Override
    public List<Key> getCollectionKeyByCollectionId(String collectionId) {
        return keyDao.getKeyListByCollectionId(collectionId);
    }
}
