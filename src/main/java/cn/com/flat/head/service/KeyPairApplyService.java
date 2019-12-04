package cn.com.flat.head.service;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.DeviceKeyPair;

/**
 * Created by panzhuowen on 2019/12/2.
 */
public interface KeyPairApplyService {

    DeviceKeyPair applyKeyPair(String alg);

    BooleanCarrier bindKey(String pubKey, String cert, String deviceCode);

    BooleanCarrier revokeKey(String pubKey, String reason);

}
