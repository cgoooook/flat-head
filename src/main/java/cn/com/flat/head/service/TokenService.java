package cn.com.flat.head.service;

import cn.com.flat.head.pojo.AccessToken;

/**
 * Created by panzhuowen on 2019/12/8.
 */
public interface TokenService {

    String accessToken(String cid);

    void setClientAccessInfo(AccessToken accessInfo);

    void generateToken(AccessToken token) throws Exception;

    String checkToken(String token);

    String convertKeyEnc(String key, String cid);

    String getCid(String token);

}
