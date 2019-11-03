package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyCollectionDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
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
}
