package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.DeviceKeyPair;

/**
 * Created by panzhuowen on 2019/12/2.
 */
public interface KeyPairApplyDao {

    DeviceKeyPair applyKeyPair(String alg);

}
