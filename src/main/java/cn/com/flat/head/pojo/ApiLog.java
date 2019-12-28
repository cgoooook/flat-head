package cn.com.flat.head.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@Data
public class ApiLog {

    private String logId;

    private String url;

    private int result;

    private String momo;

    private Date accessTime;

}
