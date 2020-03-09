package cn.com.flat.head.crypto;

import cn.com.flat.head.pojo.Key;
import cn.com.flat.head.sdf.SCrypto;
import cn.com.flat.head.sdf.Session;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SMechanism;
import cn.com.flat.head.sdf.util.Arrays;
import cn.com.flat.head.sdf.util.encoders.Hex;

import java.security.MessageDigest;
import java.util.List;

/**
 * Created by panzhuowen on 2019/10/28.
 */
public class CryptoInstance {

    private static CryptoInstance cryptoInstance = new CryptoInstance();

    private static Session session;

    private CryptoInstance() {

    }

    static {
        try {
//            session = SCrypto.getSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CryptoInstance getCryptoInstance() {
        return cryptoInstance;
    }

    public FSecretKey generateKey(String algorithm , int length) throws CryptoException {
        try {
            FSecretKey fSecretKey = new FSecretKey();
            SKey sKey = session.generateKey(new SMechanism(covertSymAlgGen(algorithm)), length);
            byte[] key = sKey.getKey();
            byte[] checkValue = getCheckValue(algorithm, length, sKey);
            fSecretKey.setAlgorithm(algorithm);
            fSecretKey.setKey(key);
            fSecretKey.setCheckValue(checkValue);
            return fSecretKey;
        } catch (Exception e) {
            throw new CryptoException("generate key error, cause by", e);
        }
    }

    private byte[] getCheckValue(String algorithm, int length, SKey sKey) throws Exception {
        byte[] encrypt = session.encrypt(new SMechanism(covertSymAlgEnc(algorithm)),
                sKey, getCheckValueInput(length));
        byte[] checkValue = new byte[8];
        System.arraycopy(encrypt, 0, checkValue, 0, 8);
        return checkValue;
    }

    public FSecretKey deriveKey(Key key, String derives) throws CryptoException {
        SKey sKey = new SKey(SKey.SKK_SM4_KEY);
        sKey.setKey(Hex.decode(key.getKeyValue()));
        byte[] deriveCompent = getDeriveCompent(derives);
        try {
            FSecretKey fSecretKey = new FSecretKey();
            byte[] encrypt = session.encrypt(new SMechanism(covertSymAlgEnc(key.getKeyAlg())), sKey, deriveCompent);
            SKey deriverKey = new SKey(SKey.SKK_SM4_KEY, encrypt);
            byte[] checkValue = getCheckValue(key.getKeyAlg(), key.getLength(), deriverKey);
            fSecretKey.setAlgorithm(fSecretKey.getAlgorithm());
            fSecretKey.setKey(encrypt);
            fSecretKey.setCheckValue(checkValue);
            return fSecretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public FSecretKey composeKey(List<String> composes, String algorithm) throws CryptoException {
        try {
            FSecretKey fSecretKey = new FSecretKey();
            byte[] tempKey = new byte[16];
            for (String compose : composes) {
                if (compose.getBytes().length != 16) {
                    throw new CryptoException("compose must be 16 bytes");
                }
                or(tempKey, compose.getBytes());
            }

            SKey sKey = new SKey(SKey.SKK_SM4_KEY, tempKey);
            byte[] checkValue = getCheckValue(algorithm, 16, sKey);
            fSecretKey.setCheckValue(checkValue);
            fSecretKey.setKey(tempKey);

            return fSecretKey;
        } catch (CryptoException e) {
            throw e;
        } catch (Exception e) {
            throw new CryptoException("compose key error", e);
        }
    }

    private void or(byte[] temp, byte[] compose) {
        for (int i = 0 ; i < 16; i++) {
            temp[i] = (byte) (temp[i] ^ compose[i]);
        }
    }

    public FKeyPair generateKeyPair(String algorithm) throws CryptoException {
        return null;
    }

    private int covertSymAlgGen(String alg) {
        switch (alg) {
            case "SM4":
                return SMechanism.SKM_SM4_KEY_GEN;
        }

        return -1;
    }

    private byte[] protectKey(byte[] key) {
        return null;
    }

    private byte[] getDeriveCompent(String derive) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5", "BC");
            digest.update(derive.getBytes());
            return digest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private int covertSymAlgEnc(String alg) {
        switch (alg) {
            case "SM4":
                return SMechanism.SKM_SM4_ECB;
            case "AES":
                return -2;
        }

        return -1;
    }

    private byte[] getCheckValueInput(int length) {
        int len = length / 8;
        byte[] input = new byte[len];
        Arrays.fill(input, (byte)0x0);
        return input;
    }


}
