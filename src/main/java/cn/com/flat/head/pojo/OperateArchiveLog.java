package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OperateArchiveLog {
    private String logId;
    private String operateType;
    private String operateUser;
    private Date operateTime;
    private int operatorResult;
    private String operateContent;
    private String contentHmac;
    private int auditFlag;
    private String operateTimeBegin;
    private String operateTimeEnd;
    private String strategy;
    private String maxActive;
}
