package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.KeyCollection;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/3.
 */
public interface KeyCollectionService {

    List<KeyCollection> getKeyCollectionListPage(Pageable pageable, KeyCollection collection);

}