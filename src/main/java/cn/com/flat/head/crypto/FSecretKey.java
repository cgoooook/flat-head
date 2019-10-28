package cn.com.flat.head.crypto;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/10/28.
 */
@Data
public class FSecretKey {

    private String algorithm;

    private int length;

    private byte[] key;

    private byte[] checkValue;

}
