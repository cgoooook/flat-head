package cn.com.flat.head.service.impl;

import cn.com.flat.head.crypto.CryptoException;
import cn.com.flat.head.crypto.FKeyPair;
import cn.com.flat.head.crypto.FSecretKey;
import cn.com.flat.head.dal.ConfigDao;
import cn.com.flat.head.sdf.util.Arrays;
import cn.com.flat.head.service.KeyGenService;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.List;
import java.util.Random;

/**
 * Created by panzhuowen on 2019/11/23.
 */
@Service
public class KeyGenServiceImpl implements KeyGenService {

    private static Logger logger = LoggerFactory.getLogger(KeyGenServiceImpl.class);

    @Autowired
    private ConfigDao configDao;

    
    public FSecretKey generateRandomKey(String alg, int length) throws Exception {
        Random random = new SecureRandom();
        int keyLength = length / 8;
        byte[] key = new byte[keyLength];
        random.nextBytes(key);
        FSecretKey fSecretKey = new FSecretKey();
        fSecretKey.setKey(getEncKey(key));
        fSecretKey.setAlgorithm(alg);
        fSecretKey.setCheckValue(getCheckValue(alg, key, keyLength));
        return fSecretKey;
    }

    
    public FSecretKey deriveKey(cn.com.flat.head.pojo.Key rootKey, String deriveParams) throws Exception {
        byte[] plainKey = getPlainKey(Hex.decode(rootKey.getKeyValue()));
        byte[] deriveCompent = getDeriveCompent(deriveParams);
        Cipher cipher = Cipher.getInstance(rootKey.getKeyAlg() + "/ECB/NoPadding", "BC");
        Key keySpec = new SecretKeySpec(plainKey, rootKey.getKeyAlg());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] bytes = cipher.doFinal(deriveCompent);
        byte[] checkValue = getCheckValue(rootKey.getKeyAlg(), bytes, rootKey.getLength());
        byte[] encKey = getEncKey(bytes);
        FSecretKey fSecretKey = new FSecretKey();
        fSecretKey.setKey(getEncKey(encKey));
        fSecretKey.setAlgorithm(rootKey.getKeyAlg());
        fSecretKey.setCheckValue(checkValue);
        return fSecretKey;

    }

    
    public FSecretKey composeKey(List<String> composes, String algorithm) throws Exception {
        FSecretKey fSecretKey = new FSecretKey();
        byte[] tempKey = new byte[16];
        for (String compose : composes) {
            if (compose.getBytes().length != 16) {
                throw new CryptoException("compose must be 16 bytes");
            }
            or(tempKey, compose.getBytes());
        }
        byte[] checkValue = getCheckValue(algorithm, tempKey, 16);
        fSecretKey.setCheckValue(checkValue);
        fSecretKey.setKey(getEncKey(tempKey));
        return fSecretKey;
    }

    public FKeyPair genKeyPair(String algorithm, int keyLength) throws Exception {
        if (algorithm.equalsIgnoreCase("RSA")) {
            return genRSAKeyPair(keyLength);
        } else {
            return genSM2Key();
        }
    }

    private void or(byte[] temp, byte[] compose) {
        for (int i = 0 ; i < 16; i++) {
            temp[i] = (byte) (temp[i] ^ compose[i]);
        }
    }

    private byte[] getCheckValue(String alg, byte[] key, int keyLength) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg + "/ECB/NoPadding", "BC");
            Key keySpec = new SecretKeySpec(key, alg);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] bytes = cipher.doFinal(getCheckValueInput(keyLength));
            byte[] checkValue = new byte[8];
            System.arraycopy(bytes, 0, checkValue, 0, 8);
            return checkValue;
        } catch (Exception e) {
            logger.error("get key checkValue error", e);
            throw e;
        }
    }

    private byte[] getCheckValueInput(int length) {
        int len = length;
        byte[] input = new byte[len];
        Arrays.fill(input, (byte) 0x0);
        return input;
    }

    private byte[] getEncKey(byte[] key) throws Exception {
        try {
            String dmk = configDao.getDMK();
            byte[] plainDMK = getPlainDMK(dmk);
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", "BC");
            Key keySpec = new SecretKeySpec(plainDMK, "SM4");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(key);
        } catch (Exception e) {
            logger.error("enc key error", e);
            throw e;
        }
    }

    //todo 解密DMK当有硬件设备的时候
    private byte[] getPlainDMK(String dmk) {
        return Hex.decode(dmk);
    }

    private byte[] getPlainKey(byte[] key) throws Exception {
        try {
            String dmk = configDao.getDMK();
            byte[] plainDMK = getPlainDMK(dmk);
            Cipher cipher = Cipher.getInstance("SM4/ECB/NoPadding", "BC");
            Key keySpec = new SecretKeySpec(plainDMK, "SM4");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(key);
        } catch (Exception e) {
            logger.error("get key plaintext error", e);
            throw e;
        }

    }

    private byte[] getDeriveCompent(String derive) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5", "BC");
            digest.update(derive.getBytes());
            return digest.digest();
        } catch (Exception e) {
            logger.error("get key derive error", e);
            throw e;
        }

    }

    private FKeyPair genRSAKeyPair(int size)  throws Exception {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(size);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey aPrivate = keyPair.getPrivate();
            FKeyPair fKeyPair = new FKeyPair();
            fKeyPair.setAlgorithm("RSA");
            fKeyPair.setPrivateKey(getEncKey(aPrivate.getEncoded()));
            fKeyPair.setPublicKey(keyPair.getPublic().getEncoded());
            return fKeyPair;
        } catch (Exception e) {
            logger.error("generate rsa error", e);
            throw e;
        }
    }

    private FKeyPair genSM2Key() throws Exception {
        try {
            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(sm2Spec);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey aPrivate = keyPair.getPrivate();
            FKeyPair fKeyPair = new FKeyPair();
            fKeyPair.setAlgorithm("SM2");
            fKeyPair.setPrivateKey(getEncKey(aPrivate.getEncoded()));
            fKeyPair.setPublicKey(keyPair.getPublic().getEncoded());
            return fKeyPair;
        } catch (Exception e) {
            logger.error("generate rsa error", e);
            throw e;
        }
    }


}
