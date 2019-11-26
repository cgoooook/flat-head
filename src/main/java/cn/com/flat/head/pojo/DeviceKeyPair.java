package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/11/26.
 */
@Data
public class DeviceKeyPair {

    private String id;

    private String alg;

    private String attr;

    private String pubKey;

    private String priKey;

}
