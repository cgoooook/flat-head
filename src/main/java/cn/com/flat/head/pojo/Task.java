package cn.com.flat.head.pojo;

import lombok.Data;

@Data
public class Task {

    private int id;
    private String taskName;
    private String algorithm;
    private String plannedQuantity;
    private String currentQuantity;
    private int length;
    private String status;
    private int auditFlag;

    private String attr;


}
