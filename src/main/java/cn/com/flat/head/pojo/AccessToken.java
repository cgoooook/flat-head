package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/12/8.
 */
@Data
public class AccessToken {

    private String cid;

    private String key;

    private long accessTime;

    private String rData;

    private String token;
}
