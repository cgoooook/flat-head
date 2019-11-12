package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by panzhuowen on 2019/10/29.
 */
@Data
public class Key {

    private String keyId;

    private String keyName;

    private String keyAlg;

    private int length;

    private  int version;

    private String checkValue;

    private String keyValue;

    private String createBy;

    private String templateId;

    private String templateName;

    private String orgId;

    private String orgName;

    private String orgCode;

    private int status;

    private String generate;

    private String mode;

    private int composeNum;

    private List<String> composes;

    private String deriveParam;

}
