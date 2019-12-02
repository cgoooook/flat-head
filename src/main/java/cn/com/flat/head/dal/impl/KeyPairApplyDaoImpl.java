package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyPairApplyDao;
import cn.com.flat.head.dal.mappers.KeyPairApplyMapper;
import cn.com.flat.head.dal.mappers.KeyPairMapper;
import cn.com.flat.head.pojo.DeviceKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by panzhuowen on 2019/12/2.
 */
@Repository
public class KeyPairApplyDaoImpl implements KeyPairApplyDao {

    @Autowired
    private KeyPairApplyMapper keyPairApplyMapper;

    @Autowired
    private KeyPairMapper keyPairMapper;


    @Override
    public DeviceKeyPair applyKeyPair(String alg) {
        DeviceKeyPair deviceKeyPair = keyPairMapper.getDeviceKeyPair(alg);
        keyPairMapper.deleteKeyPair(deviceKeyPair.getId());
        keyPairApplyMapper.addKeyPair(deviceKeyPair);
        return deviceKeyPair;
    }
}
