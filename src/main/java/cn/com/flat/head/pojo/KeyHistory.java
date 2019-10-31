package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/10/29.
 */
@Data
public class KeyHistory {

    private String keyHistoryId;

    private String keyId;

    private String keyName;

    private String keyAlg;

    private int length;

    private  int version;

    private String checkValue;

    private String keyValue;

    private String createBy;

    private String templateId;

    private String orgId;

    private int status;

}
