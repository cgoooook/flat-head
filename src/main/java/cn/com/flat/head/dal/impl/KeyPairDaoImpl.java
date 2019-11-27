package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.dal.mappers.KeyPairMapper;
import cn.com.flat.head.pojo.DeviceKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by panzhuowen on 2019/11/27.
 */
@Repository
public class KeyPairDaoImpl implements KeyPairDao {

    @Autowired
    private KeyPairMapper keyPairMapper;

    @Override
    public int getKeyPairNum(String alg, String length) {
        if ("RSA".equalsIgnoreCase(alg)) {
            return keyPairMapper.getKeyPairNumAndLength(alg, length);
        }
        return keyPairMapper.getKeyPairNum(alg);
    }

    @Override
    public int addKeyPair(DeviceKeyPair deviceKeyPair) {
        return keyPairMapper.addKeyPair(deviceKeyPair);
    }
}
