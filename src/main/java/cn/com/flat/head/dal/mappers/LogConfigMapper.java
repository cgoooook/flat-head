package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.LogConfig;
import cn.com.flat.head.pojo.SysLogo;

import java.util.List;


@RepositoryImpl
public interface LogConfigMapper {

    List<LogConfig> getLogConfig();



    void editLogLevel(String logLevel);

    void editLogDays(String logDays);

    void updateCopyright(String copyright);

    void updateLogo();

    void insertLogo(SysLogo sysLogo);

    SysLogo getUiInfo();

    LogConfig getCopyright();
}
