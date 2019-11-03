package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.KeyCollectionDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.KeyCollection;
import cn.com.flat.head.service.KeyCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/3.
 */
@Service
public class KeyCollectionServiceImpl implements KeyCollectionService {

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
        int collectionByCollectionName = keyCollectionDao.getCollectionByCollectionName(collection.getCollectionName());
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        if (collectionByCollectionName > 0) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("keyCollection.nameExits");
            return booleanCarrier;
        } else {
            booleanCarrier.setResult(keyCollectionDao.addCollection(collection) == 1);
        }

        return booleanCarrier;
    }

    @Override
    public BooleanCarrier deleteKeyCollection(String collectionId) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        int collectionCountByCollectionId = devServiceDao.getCollectionCountByCollectionId(collectionId);
        if (collectionCountByCollectionId > 0) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("keyCollection.collectionInUse");
            return booleanCarrier;
        }
        int i = keyCollectionDao.deleteCollection(collectionId);
        keyCollectionDao.deleteCollectionKeys(collectionId);
        booleanCarrier.setResult(i == 1);
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
        BooleanCarrier booleanCarrier = new BooleanCarrier();
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
        return booleanCarrier;
    }


}
