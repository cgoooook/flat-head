package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@Data
public class ApiLog {

    public ApiLog(String logId, String url, String result, String momo, Date accessTime) {
        this.logId = logId;
        this.url = url;
        this.result = result;
        this.momo = momo;
        this.accessTime = accessTime;
    }

    private String logId;

    private String url;

    private String result;

    private String momo;

    private Date accessTime;

}
