package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
@Service
public class KeyServiceImpl implements KeyService {

    @Autowired
    private KeyDao keyDao;

    @Override
    public List<Key> getKeyListPage(Pageable pageable, Key key) {
        PageableInterceptor.startPage(pageable);
        return keyDao.getKeyListPage(key);
    }
}
