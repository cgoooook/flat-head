package cn.com.flat.head.service;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Jdbc;
import cn.com.flat.head.pojo.LogConfig;

import java.util.List;

public interface ConfigService {
    BooleanCarrier  updateJdbcConfig(Jdbc jdbc);

    Jdbc getJdbcConfig();

    List<LogConfig> getLogConfig();
    

    void editLogLevel(String logLevel);

    void editLogDays(String logDays);
}
