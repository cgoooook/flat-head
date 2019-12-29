package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyPairApplyDao;
import cn.com.flat.head.dal.mappers.KeyPairApplyMapper;
import cn.com.flat.head.dal.mappers.KeyPairBindMapper;
import cn.com.flat.head.dal.mappers.KeyPairMapper;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.pojo.DeviceKeyPairBind;
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

    @Autowired
    private KeyPairBindMapper keyPairBindMapper;


    @Override
    public DeviceKeyPair applyKeyPair(String alg) {
        DeviceKeyPair deviceKeyPair = keyPairMapper.getDeviceKeyPair(alg);
        keyPairMapper.deleteKeyPair(deviceKeyPair.getId());
        keyPairApplyMapper.addKeyPair(deviceKeyPair);
        return deviceKeyPair;
    }

    @Override
    public int bindKey(DeviceKeyPairBind deviceKeyPairBind) {
        return keyPairBindMapper.addDeviceKeyPair(deviceKeyPairBind);
    }

    @Override
    public DeviceKeyPair getDeviceApplyKey(String pubKey) {
        return keyPairApplyMapper.getDeviceKeyPair(pubKey);
    }

    @Override
    public int deleteKeyPairApply(String id) {
        return keyPairApplyMapper.deleteKeyPair(id);
    }

    @Override
    public String getKeyPairId(String pubKey) {
        return keyPairBindMapper.getKeyPairIdByPubKey(pubKey);
    }

    @Override
    public int revokeKey(String keyPairId, String reason) {

        return keyPairBindMapper.revokeKey(keyPairId, reason);
    }

    @Override
    public int getRevokeKey() {
        return keyPairBindMapper.getRevokeKeyPair("SM2");
    }

    @Override
    public int getUseKey() {
        return keyPairBindMapper.getUserKeyPair("SM2");
    }

    @Override
    public int getNotUseKey() {
        return keyPairMapper.getKeyPairNum("SM2");
    }
}
