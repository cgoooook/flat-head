package cn.com.flat.head.log;

import cn.com.flat.head.pojo.ApiLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by panzhuowen on 2019/12/28.
 */
public class ApiLogBuilder {

    public static ApiLog builder(String url, boolean optResult, String optContent) {
        return new ApiLog(UUID.randomUUID().toString(), url, optResult ? "1" : "0", optContent, new Date());
    }

}
