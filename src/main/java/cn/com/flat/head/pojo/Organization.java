package cn.com.flat.head.pojo;

import lombok.Data;

@Data
public class Organization {

    private String orgId;

    private String orgName;

    private String orgCode;

    private String parentId;

    private String parentName;

    private int leaf;

}
