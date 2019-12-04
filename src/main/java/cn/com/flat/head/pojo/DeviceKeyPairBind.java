package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/12/2.
 */
@Data
public class DeviceKeyPairBind {

    private String keyPairId;

    private String deviceId;

    private String alg;

    private String attr;

    private String pubKey;

    private String priKey;

    private String certContent;

    private int status;

    private String reason;

}
