package cn.com.flat.head.crypto;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/10/28.
 */
@Data
public class FKeyPair {

    private String algorithm;

    private byte[] publicKey;

    private byte[] privateKey;

}
