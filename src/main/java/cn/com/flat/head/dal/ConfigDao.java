package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.LogConfig;
import cn.com.flat.head.pojo.SysLogo;

import java.util.List;

public interface ConfigDao {
    List<LogConfig> getLogConfig();


    void editLogLevel(String logLevel);

    void editLogDays(String logDays);

    void updateCopyright(String copyright);


    void updateLogo();

    void insertLogo(SysLogo sysLogo);

    SysLogo getUiInfo();
}
