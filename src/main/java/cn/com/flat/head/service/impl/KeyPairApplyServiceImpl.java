package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyPairApplyDao;
import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.service.KeyPairApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by panzhuowen on 2019/12/2.
 */
@Service
public class KeyPairApplyServiceImpl implements KeyPairApplyService {

    @Autowired
    private KeyPairApplyDao keyPairApplyDao;

    @Override
    public DeviceKeyPair applyKeyPair(String alg) {
        return keyPairApplyDao.applyKeyPair(alg);
    }
}
