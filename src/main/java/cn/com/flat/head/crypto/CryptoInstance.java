package cn.com.flat.head.crypto;

import cn.com.flat.head.sdf.SCrypto;
import cn.com.flat.head.sdf.Session;

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
        return null;
    }

    public FKeyPair generateKeyPair(String algorithm) throws CryptoException {
        return null;
    }


}
