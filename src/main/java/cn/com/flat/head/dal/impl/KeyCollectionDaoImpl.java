package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyCollectionDao;
import cn.com.flat.head.dal.mappers.CollectionKeysMapper;
import cn.com.flat.head.dal.mappers.KeyCollectionMapper;
import cn.com.flat.head.pojo.KeyCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
@Repository
public class KeyCollectionDaoImpl implements KeyCollectionDao {

    @Autowired
    private KeyCollectionMapper keyCollectionMapper;

    @Autowired
    private CollectionKeysMapper collectionKeysMapper;


    @Override
    public List<KeyCollection> getCollectionListPage(String orgId) {
        return keyCollectionMapper.getCollectionByOrgId(orgId);
    }

    @Override
    public KeyCollection getCollectionById(String collectionId) {
        return keyCollectionMapper.getKeyConnectionById(collectionId);
    }

    @Override
    public List<KeyCollection> getCollectionByOrgId(String orgId) {
        return keyCollectionMapper.getKeyConnectionListPage(orgId);
    }

    @Override
    public int addCollection(KeyCollection collection) {
        return keyCollectionMapper.addCollection(collection);
    }

    @Override
    public int updateCollection(KeyCollection collection) {
        return keyCollectionMapper.updateConnection(collection);
    }

    @Override
    public int deleteCollection(String collectionId) {
        return keyCollectionMapper.deleteKeyCollection(collectionId);
    }

    @Override
    public boolean addCollectionKeys(String collectionId, List<String> keys) {
        collectionKeysMapper.deleteByCollectionId(collectionId);
        collectionKeysMapper.addCollectionKeys(collectionId, keys);
        return true;
    }
}
