package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.pojo.DeviceKeyPairBind;

/**
 * Created by panzhuowen on 2019/12/2.
 */
public interface KeyPairApplyDao {

    DeviceKeyPair applyKeyPair(String alg);

    int bindKey(DeviceKeyPairBind deviceKeyPairBind);

    DeviceKeyPair getDeviceApplyKey(String pubKey);

    int deleteKeyPairApply(String id);

    String getKeyPairId(String pubKey);

    int revokeKey(String keyPairId, String reason);

}
