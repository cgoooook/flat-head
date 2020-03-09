package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.DeviceKeyPair;

@RepositoryImpl
public interface KmsDeviceKeyPairMapper {
    int getKeyPair(DeviceKeyPair deviceKeyPair);

    int getSpareNumByType(String type);
}
