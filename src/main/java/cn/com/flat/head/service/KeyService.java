package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Key;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
public interface KeyService {

    List<Key> getKeyListPage(Pageable pageable, Key key);

}
