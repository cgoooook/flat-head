package cn.com.flat.head.service.impl;

import cn.com.flat.head.pojo.AccessToken;
import cn.com.flat.head.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${rest.checkToken}")
    private String checkToken;

    private ConcurrentHashMap<String, AccessToken> tokenMap = new ConcurrentHashMap<>();


    @Override
    public String accessToken(String token) {
        return null;
    }

    @Override
    public void setClientAccessInfo(AccessToken accessInfo) {
        tokenMap.put(accessInfo.getToken(), accessInfo);
    }

    @Override
    public String checkToken(String token) {
        try {
            if (StringUtils.equalsIgnoreCase("on", checkToken)) {
                AccessToken atoken = tokenMap.get(token);
                if (atoken==null)
                    return "token check error";

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            return "token check error";
        }
    }

    @Override
    public void generateToken(AccessToken token) throws Exception
    {
        Random random = new Random();
        byte[] clientCode = new byte[16];
        token.setAccessTime(new Date().getTime());
        random.nextBytes(clientCode);
        String rData = new String(Hex.encode(clientCode));
        token.setRData(rData);

        byte[] cid = Hex.decode(token.getCid());

        byte[] tokenDigest = new byte[32];
        byte[] digestSrc = new byte[clientCode.length + cid.length];
        //随机数
        System.arraycopy(clientCode, 0, digestSrc, 0, clientCode.length);
        //CID
        System.arraycopy(cid, 0, digestSrc, clientCode.length, cid.length);
        byte[] sm3Digest = getSM3Digest(digestSrc);

        byte[] keyAndIv = Hex.decode(token.getKey());
        byte[] key = new byte[16];
        byte[] iv = new byte[16];
        System.arraycopy(keyAndIv, 0, key, 0, 16);
        System.arraycopy(keyAndIv, 16, iv, 0, 16);
        byte[] sm4CBC = getSM4CBCEncrypt(clientCode, key, iv);

        byte[] bytes = new byte[sm3Digest.length + sm4CBC.length];
        System.arraycopy(sm3Digest, 0, bytes, 0, sm3Digest.length);
        System.arraycopy(sm4CBC, 0, bytes, sm3Digest.length, sm4CBC.length);

        token.setToken(new String(Hex.encode(bytes)));

        setClientAccessInfo(token);
    }


    private byte[] getSM3Digest(byte[] src) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SM3", "BC");
        messageDigest.update(src);
        return messageDigest.digest();
    }

    private byte[] getSM4CBCDecrypt(byte[] dat, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("SM4/CBC/NoPadding", "BC");
        Key keySpec = new SecretKeySpec(key, "SM4");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(dat);
    }

    private byte[] getSM4CBCEncrypt(byte[] dat, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("SM4/CBC/NoPadding", "BC");
        Key keySpec = new SecretKeySpec(key, "SM4");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(dat);
    }
}
