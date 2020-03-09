package cn.com.flat.head.service;

import cn.com.flat.head.crypto.FKeyPair;
import cn.com.flat.head.crypto.FSecretKey;
import cn.com.flat.head.pojo.Key;

import java.util.List;

/**
 * Created by panzhuowen on 2019/11/23.
 */
public interface KeyGenService {

    FSecretKey generateRandomKey(String alg, int length) throws Exception;

    FSecretKey deriveKey(Key rootKey, String deriveParams) throws Exception;

    FSecretKey composeKey(List<String> composes, String algorithm) throws Exception;

    FKeyPair genKeyPair(String algorithm, int keyLength) throws Exception;

}
