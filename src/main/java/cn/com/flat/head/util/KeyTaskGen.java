package cn.com.flat.head.util;

import cn.com.flat.head.crypto.FKeyPair;
import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.pojo.DeviceKeyPair;
import cn.com.flat.head.pojo.Task;
import cn.com.flat.head.sdf.util.encoders.Hex;
import cn.com.flat.head.service.KeyGenService;

import java.util.UUID;

/**
 * Created by panzhuowen on 2019/11/27.
 */
public class KeyTaskGen implements Runnable {

    private KeyPairDao keyPairDao;

    private KeyGenService keyGenService;

    private Task task;

    private int generateNum;

    public KeyTaskGen(KeyPairDao keyPairDao, KeyGenService keyGenService, Task task, int generateNum) {
        this.keyPairDao = keyPairDao;
        this.keyGenService = keyGenService;
        this.task = task;
        this.generateNum = generateNum;
    }

    @Override
    public void run() {
        boolean ex = false;
        for (int i = 0; i < generateNum; i++) {
            try {
                if (ex) {
                    break;
                }
                FKeyPair fKeyPair = keyGenService.genKeyPair(task.getAlgorithm(), task.getLength());
                DeviceKeyPair deviceKeyPair = new DeviceKeyPair();
                deviceKeyPair.setId(UUID.randomUUID().toString());
                deviceKeyPair.setAlg(task.getAlgorithm());
                deviceKeyPair.setAttr(task.getLength() + "");
                deviceKeyPair.setPubKey(new String(Hex.encode(fKeyPair.getPublicKey())));
                deviceKeyPair.setPriKey(new String(Hex.encode(fKeyPair.getPrivateKey())));
                keyPairDao.addKeyPair(deviceKeyPair);
            } catch (Exception e) {
                ex = true;
                e.printStackTrace();
            }
        }
    }
}
