package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.ConfigDao;
import cn.com.flat.head.dal.mappers.LogConfigMapper;
import cn.com.flat.head.pojo.LogConfig;
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


}
