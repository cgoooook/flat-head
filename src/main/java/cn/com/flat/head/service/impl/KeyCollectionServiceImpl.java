package cn.com.flat.head.service.impl;

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
        return null;
    }

    @Override
    public List<KeyCollection> getKeyCollectionByOrgId(String orgId) {
        return keyCollectionDao.getCollectionByOrgId(orgId);
    }


}
