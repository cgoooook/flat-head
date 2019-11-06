package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Key;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/6.
 */
public interface KeyDao {

    List<Key> getKeyListPage(Key key);

}
