package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.dal.mappers.KeyMapper;
import cn.com.flat.head.pojo.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
@Repository
public class KeyDaoImpl implements KeyDao {

    @Autowired
    private KeyMapper keyMapper;

    @Override
    public List<Key> getKeyListPage(Key key) {
        return keyMapper.getKeyListPage(key);
    }
}
