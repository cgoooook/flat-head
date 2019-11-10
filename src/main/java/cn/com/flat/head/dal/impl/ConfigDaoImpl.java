package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.ConfigDao;
import cn.com.flat.head.dal.mappers.LogConfigMapper;
import cn.com.flat.head.pojo.LogConfig;
import cn.com.flat.head.pojo.SysLogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ConfigDaoImpl implements ConfigDao {
    @Autowired
    LogConfigMapper logConfigMapper;
    @Override
    public List<LogConfig> getLogConfig() {
        return logConfigMapper.getLogConfig();
    }


    @Override
    public void editLogLevel(String logLevel) {
        logConfigMapper.editLogLevel(logLevel);
    }

    @Override
    public void editLogDays(String logDays) {
        logConfigMapper.editLogDays(logDays);
    }

    @Override
    public void updateCopyright(String copyright) {
        logConfigMapper.updateCopyright(copyright);
    }

    @Override
    public void updateLogo() {
        logConfigMapper.updateLogo();
    }

    @Override
    public void insertLogo(SysLogo sysLogo) {
        logConfigMapper.insertLogo(sysLogo);
    }

    @Override
    public SysLogo getUiInfo() {

        SysLogo uiInfo = logConfigMapper.getUiInfo();

        return uiInfo;
    }

    @Override
    public LogConfig getCopyright() {
        LogConfig logConfig = logConfigMapper.getCopyright();
        return logConfig;
    }


}
