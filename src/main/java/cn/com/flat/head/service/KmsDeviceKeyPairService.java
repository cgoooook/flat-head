package cn.com.flat.head.service;

import cn.com.flat.head.pojo.DeviceKeyPair;

public interface KmsDeviceKeyPairService {
    int getKeyPair(DeviceKeyPair deviceKeyPair);

    int getSpareNumByType(String sm2);
}
