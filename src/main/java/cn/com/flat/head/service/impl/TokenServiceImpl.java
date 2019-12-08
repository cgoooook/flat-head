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

/**
 * Created by panzhuowen on 2019/12/8.
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${rest.checkToken}")
    private String checkToken;

    private ConcurrentHashMap<String, AccessToken> cidMap = new ConcurrentHashMap<>();


    @Override
    public String accessToken(String cid) {
        return null;
    }

    @Override
    public void setClientAccessInfo(AccessToken accessInfo) {
        cidMap.put(accessInfo.getCid(), accessInfo);
    }

    @Override
    public String checkToken(String token) {
        try {
            if (StringUtils.equalsIgnoreCase("on", checkToken)) {
                byte[] tokenBytes = Hex.decode(token);
                if (tokenBytes.length < 32) {
                    return "token check error";
                }
                byte[] clientIdBytes = new byte[32];
                System.arraycopy(tokenBytes, tokenBytes.length - 32, clientIdBytes, 0, 32);
                String cid = new String(Hex.encode(clientIdBytes));
                AccessToken accessToken = cidMap.get(cid);
                if (accessToken == null) {
                    return "client not access, please access first.";
                }
                if (((new Date().getTime() - accessToken.getAccessTime()) / 1000 / 60) > 30) {
                    return "token out time.";
                }
                byte[] tokenDigest = new byte[32];
                System.arraycopy(tokenBytes, tokenBytes.length - 64, tokenDigest, 0, 32);
                String rData = accessToken.getRData();
                byte[] rDataBytes = Hex.decode(rData);
                byte[] digestSrc = new byte[rDataBytes.length + clientIdBytes.length];
                System.arraycopy(rDataBytes, 0, digestSrc, 0, rDataBytes.length);
                System.arraycopy(clientIdBytes, 0, digestSrc, digestSrc.length - rDataBytes.length, clientIdBytes.length);
                byte[] sm3Digest = getSM3Digest(digestSrc);
                if (Arrays.equals(sm3Digest, tokenDigest)) {
                    return "token check error";
                }
                byte[] cipherData = new byte[tokenBytes.length - 64];
                System.arraycopy(tokenBytes, 0, cipherData, 0, cipherData.length);
                byte[] keyAndIv = Hex.decode(accessToken.getKey());
                byte[] key = new byte[16];
                byte[] iv = new byte[16];
                System.arraycopy(keyAndIv, 0, key, 0, 16);
                System.arraycopy(keyAndIv, 16, iv, 0, 16);
                byte[] sm4CBCDecrypt = getSM4CBCDecrypt(cipherData, key, iv);
                if (Arrays.equals(sm4CBCDecrypt, rDataBytes)) {
                    return "token check error";
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            return "token check error";
        }
    }

    private byte[] getSM3Digest(byte[] src) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SM3", "BC");
        messageDigest.update(src);
        return messageDigest.digest();
    }

    private byte[] getSM4CBCDecrypt(byte[] out, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("SM4/CBC/NoPadding", "BC");
        Key keySpec = new SecretKeySpec(key, "SM4");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(out);
    }
}
