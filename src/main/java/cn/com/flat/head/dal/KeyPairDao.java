package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.DeviceKeyPair;

/**
 * Created by panzhuowen on 2019/11/27.
 */
public interface KeyPairDao {

    int getKeyPairNum(String alg, String length);

    int addKeyPair(DeviceKeyPair deviceKeyPair);

}
