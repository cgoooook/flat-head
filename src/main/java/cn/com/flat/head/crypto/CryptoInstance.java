package cn.com.flat.head.crypto;

import cn.com.flat.head.sdf.SCrypto;
import cn.com.flat.head.sdf.Session;
import cn.com.flat.head.sdf.cipher.SKey;
import cn.com.flat.head.sdf.cipher.SMechanism;
import cn.com.flat.head.sdf.util.Arrays;
import cn.com.flat.head.sdf.util.encoders.Base64;
import cn.com.flat.head.sdf.util.encoders.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

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
            byte[] checkValue = getCheckValue(algorithm, length, sKey);
            fSecretKey.setAlgorithm(algorithm);
            fSecretKey.setKey(key);
            fSecretKey.setCheckValue(checkValue);
            return fSecretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getCheckValue(String algorithm, int length, SKey sKey) throws Exception {
        byte[] encrypt = session.encrypt(new SMechanism(covertSymAlgEnc(algorithm)),
                sKey, getCheckValueInput(length));
        byte[] checkValue = new byte[8];
        System.arraycopy(encrypt, 0, checkValue, 0, 8);
        return checkValue;
    }

    public FSecretKey deriveKey(FSecretKey secretKey, String derives) throws CryptoException {
        int algEnc = covertSymAlgEnc(secretKey.getAlgorithm());
        byte[] key = null;
        SKey sKey = new SKey(SKey.SKK_SM4_KEY);
        sKey.setKey(secretKey.getKey());
        byte[] deriveCompent = getDeriveCompent(derives);
        try {
            byte[] encrypt = session.encrypt(new SMechanism(algEnc), sKey, deriveCompent);
            SKey dk = new SKey(SKey.SKK_SM4_KEY);
            dk.setKey(encrypt);
            FSecretKey fSecretKey = new FSecretKey();
            fSecretKey.setLength(secretKey.getLength());
            fSecretKey.setAlgorithm(secretKey.getAlgorithm());
            fSecretKey.setKey(encrypt);
            byte[] checkValue = getCheckValue(fSecretKey.getAlgorithm(), fSecretKey.getLength(), dk);
            fSecretKey.setCheckValue(checkValue);
            return fSecretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public FKeyPair generateKeyPair(String algorithm) throws CryptoException {
        return null;
    }



    public static void main(String[] args) throws Exception {
//        FSecretKey fSecretKey = CryptoInstance.getCryptoInstance().generateKey("SM4", 128);
//        System.out.println(new String(Hex.encode(fSecretKey.getKey())));
//        System.out.println(new String(Hex.encode(fSecretKey.getCheckValue())));
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest digest = MessageDigest.getInstance("MD5", "BC");
        digest.update("123".getBytes());
        byte[] digest1 = digest.digest();
        System.out.println(digest1.length);
    }

    private int covertSymAlgGen(String alg) {
        switch (alg) {
            case "SM4":
                return SMechanism.SKM_SM4_KEY_GEN;
        }

        return -1;
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
