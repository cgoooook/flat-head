package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KmsDeviceKeyPairDao;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.service.KmsDeviceKeyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KmsDeviceKeyPairServiceImpl implements KmsDeviceKeyPairService {
    @Autowired
    KmsDeviceKeyPairDao kmsDeviceKeyPairDao;

    @Override
    public int getKeyPair(DeviceKeyPair deviceKeyPair) {
        return kmsDeviceKeyPairDao.getKeyPair(deviceKeyPair);
    }

    @Override
    public int getSpareNumByType(String sm2) {
        return kmsDeviceKeyPairDao.getSpareNumByType(sm2);
    }
}
