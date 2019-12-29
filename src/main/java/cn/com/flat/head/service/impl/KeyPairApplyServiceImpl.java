package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.KeyPairApplyDao;
import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.pojo.DeviceKeyPairBind;
import cn.com.flat.head.service.KeyPairApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panzhuowen on 2019/12/2.
 */
@Service
public class KeyPairApplyServiceImpl implements KeyPairApplyService {

    @Autowired
    private KeyPairApplyDao keyPairApplyDao;

    @Autowired
    private DevServiceDao devServiceDao;

    @Override
    public DeviceKeyPair applyKeyPair(String alg) {
        return keyPairApplyDao.applyKeyPair(alg);
    }

    @Override
    public BooleanCarrier bindKey(String pubKey, String cert, String deviceCode) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        Device devByDevCode = devServiceDao.getDevByDevCode(deviceCode);
        if (devByDevCode == null) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("can not find device by code");
            return booleanCarrier;
        }
        pubKey = pubKey.toLowerCase();
        DeviceKeyPair deviceApplyKey = keyPairApplyDao.getDeviceApplyKey(pubKey);
        if (deviceApplyKey == null) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("can not find bind key by public key");
            return booleanCarrier;
        }
        keyPairApplyDao.deleteKeyPairApply(deviceApplyKey.getId());
        DeviceKeyPairBind deviceKeyPairBind = new DeviceKeyPairBind();
        deviceKeyPairBind.setAttr(deviceApplyKey.getAttr());
        deviceKeyPairBind.setCertContent(cert);
        deviceKeyPairBind.setDeviceId(devByDevCode.getDeviceId());
        deviceKeyPairBind.setAlg(deviceApplyKey.getAlg());
        deviceKeyPairBind.setPriKey(deviceApplyKey.getPriKey());
        deviceKeyPairBind.setPubKey(deviceApplyKey.getPubKey());
        deviceKeyPairBind.setKeyPairId(deviceApplyKey.getId());
        keyPairApplyDao.bindKey(deviceKeyPairBind);
        booleanCarrier.setResult(true);
        return booleanCarrier;
    }

    @Override
    public BooleanCarrier revokeKey(String pubKey, String reason) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        pubKey = pubKey.toLowerCase();
        String keyPairId = keyPairApplyDao.getKeyPairId(pubKey);
        if (StringUtils.isBlank(keyPairId)) {
            booleanCarrier.setResult(false);
            booleanCarrier.setMessage("can not find key by public key");
            return booleanCarrier;
        }
        keyPairApplyDao.revokeKey(keyPairId, reason);
        booleanCarrier.setResult(true);
        return booleanCarrier;
    }

    @Override
    public Map<String, Object> getKeyNumber() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("useKey", keyPairApplyDao.getUseKey());
        ret.put("notUseKey", keyPairApplyDao.getNotUseKey());
        ret.put("revokeKey", keyPairApplyDao.getRevokeKey());
        return ret;
    }
}
