package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.KeyCollectionDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyCollection;
import cn.com.flat.head.service.KeyCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/3.
 */
@Service
public class KeyCollectionServiceImpl implements KeyCollectionService {

    private static Logger logger = LoggerFactory.getLogger(KeyCollectionServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Autowired
    private KeyCollectionDao keyCollectionDao;

    @Autowired
    private DevServiceDao devServiceDao;

    @Override
    public List<KeyCollection> getKeyCollectionListPage(Pageable pageable, KeyCollection collection) {
        PageableInterceptor.startPage(pageable);
        return keyCollectionDao.getCollectionListPage(collection);
    }

    @Override
    public BooleanCarrier addCollection(KeyCollection collection) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        boolean result = true;
        try {
            int collectionByCollectionName = keyCollectionDao.getCollectionByCollectionName(collection.getCollectionName());
            if (collectionByCollectionName > 0) {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("keyCollection.nameExits");
                return booleanCarrier;
            } else {
                booleanCarrier.setResult(keyCollectionDao.addCollection(collection) == 1);
            }
        } catch (Exception e) {
            logger.error("add key collection error", e);
            result = false;
            booleanCarrier.setResult(false);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addCollection, result, "add collection name is" + collection.getCollectionName()));
        }
        return booleanCarrier;
    }

    @Override
    public BooleanCarrier deleteKeyCollection(String collectionId) {
        boolean result = true;
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            int collectionCountByCollectionId = devServiceDao.getCollectionCountByCollectionId(collectionId);
            if (collectionCountByCollectionId > 0) {
                booleanCarrier.setResult(false);
                booleanCarrier.setMessage("keyCollection.collectionInUse");
                return booleanCarrier;
            }
            int i = keyCollectionDao.deleteCollection(collectionId);
            keyCollectionDao.deleteCollectionKeys(collectionId);
            booleanCarrier.setResult(i == 1);
        } catch (Exception e) {
            booleanCarrier.setResult(false);
            logger.error("delete collection error", e);
            result = false;
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.deleteCollection, result, "delete collection id is" + collectionId));
        }
        return booleanCarrier;
    }

    @Override
    public List<KeyCollection> getKeyCollectionByOrgId(String orgId) {
        return keyCollectionDao.getCollectionByOrgId(orgId);
    }

    @Override
    public KeyCollection getCollectionByCollectionId(String collectionId) {
        return keyCollectionDao.getCollectionById(collectionId);
    }

    @Override
    public BooleanCarrier updateCollection(KeyCollection collection) {
        boolean result = true;
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        try {
            KeyCollection collectionById = keyCollectionDao.getCollectionById(collection.getCollectionId());
            if (!collectionById.getCollectionName().equals(collection.getCollectionName())) {
                int collectionByCollectionName = keyCollectionDao.getCollectionByCollectionName(collection.getCollectionName());
                if (collectionByCollectionName > 0) {
                    booleanCarrier.setResult(false);
                    booleanCarrier.setMessage("keyCollection.collectionInUse");
                    return booleanCarrier;
                }
            }
            int i = keyCollectionDao.updateCollection(collection);
            booleanCarrier.setResult(i == 1);
        } catch (Exception e) {
            booleanCarrier.setResult(false);
            result = false;
            logger.error("update collection error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateCollection, result, "update collection name is" + collection.getCollectionName()));
        }
        return booleanCarrier;
    }

    @Override
    public boolean delSubKey(String collectionId, String keyId) {
        return keyCollectionDao.delSubKey(collectionId, keyId) >= 1;
    }

    @Override
    public KeyCollection getCollectionByName(String name) {
        return keyCollectionDao.getCollectionByName(name);
    }

    @Override
    public boolean addSubKeys(List<String> keyIds, String collectionId) {
        return keyCollectionDao.addSubKeys(keyIds, collectionId) >= 1;
    }


}
