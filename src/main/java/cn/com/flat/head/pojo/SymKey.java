package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SymKey {

    private String keyId;

    private String keyAlg;

    private int length;

    private int version;

    private String checkValue;

    private String keyValue;

    private String createBy;

    private int status;

}
