package cn.com.flat.head.log;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Data
public class Log {

    private String logId;

    private String operateType;

    private String operateUser;

    private Date operateTime;

    private int operateResult;

    private String operateContent;

    private String contentHmac;

    private int auditFlag;

    public Log(String operateType, String operateUser, Date operateTime, int operateResult, String operateContent) {
        this.logId = UUID.randomUUID().toString();
        this.operateType = operateType;
        this.operateUser = operateUser;
        this.operateTime = operateTime;
        this.operateResult = operateResult;
        this.operateContent = operateContent;
    }
}
