package cn.com.flat.head.cache;

import cn.com.flat.head.pojo.AccessToken;

/**
 * Created by panzhuowen on 2019/12/8.
 */
public interface ClientCache {

    AccessToken getAccessToken(String cid);

}
