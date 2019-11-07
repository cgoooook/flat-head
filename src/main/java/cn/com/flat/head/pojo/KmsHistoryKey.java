package cn.com.flat.head.pojo;

import lombok.Data;

/**
 * Created by panzhuowen on 2019/11/5.
 */
@Data
public class KmsHistoryKey {

    private String keyHistoryId;

    private String keyId;

    private String keyName;

    private String keyAlg;

    private int keyLength;

    private int version;

    private String checkValue;

    private String keyValue;

    private String createBy;

    private String templateId;

    private String orgId;

    private int status;

}
