package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KmsDeviceKeyPairDao;
import cn.com.flat.head.dal.mappers.KmsDeviceKeyPairMapper;
import cn.com.flat.head.pojo.DeviceKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KmsDeviceKeyPairDaoImpl implements KmsDeviceKeyPairDao {
    @Autowired
    private KmsDeviceKeyPairMapper kmsDeviceKeyPairMapper;
    @Override
    public int getKeyPair(DeviceKeyPair deviceKeyPair) {
        return kmsDeviceKeyPairMapper.getKeyPair(deviceKeyPair);
    }

    @Override
    public int getSpareNumByType(String type) {
        return kmsDeviceKeyPairMapper.getSpareNumByType(type);
    }
}
