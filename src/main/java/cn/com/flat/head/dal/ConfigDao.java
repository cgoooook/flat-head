package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.LogConfig;

import java.util.List;

public interface ConfigDao {
    List<LogConfig> getLogConfig();


    void editLogLevel(String logLevel);

    void editLogDays(String logDays);
}
