package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.DeviceKeyPair;

public interface KmsDeviceKeyPairDao {
    int getKeyPair(DeviceKeyPair deviceKeyPair);

    int getSpareNumByType(String sm2);
}
