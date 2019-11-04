package cn.com.flat.head.crypto;

import cn.com.flat.head.sdf.SCrypto;
import cn.com.flat.head.sdf.Session;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SMechanism;
import cn.com.flat.head.sdf.util.Arrays;
import cn.com.flat.head.sdf.util.encoders.Base64;
import cn.com.flat.head.sdf.util.encoders.Hex;

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
            session = SCrypto.getSession();
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
            byte[] encrypt = session.encrypt(new SMechanism(SMechanism.SKM_SM4_ECB),
                    sKey, getCheckValueInput(length));
            byte[] checkValue = new byte[8];
            System.arraycopy(encrypt, 0, checkValue, 0, 8);
            fSecretKey.setAlgorithm(algorithm);
            fSecretKey.setKey(key);
            fSecretKey.setCheckValue(checkValue);
            return fSecretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FSecretKey driveKey(FSecretKey secretKey, String[] drives) throws CryptoException {


        return null;
    }

    public FKeyPair generateKeyPair(String algorithm) throws CryptoException {
        return null;
    }

    public static void main(String[] args) {
        FSecretKey fSecretKey = CryptoInstance.getCryptoInstance().generateKey("SM4", 128);
        System.out.println(new String(Hex.encode(fSecretKey.getKey())));
        System.out.println(new String(Hex.encode(fSecretKey.getCheckValue())));
    }

    private int covertSymAlgGen(String alg) {
        switch (alg) {
            case "SM4":
                return SMechanism.SKM_SM4_KEY_GEN;
        }

        return -1;
    }

    public byte[] getCheckValueInput(int length) {
        int len = length / 8;
        byte[] input = new byte[len];
        Arrays.fill(input, (byte)0x0);
        return input;
    }


}
