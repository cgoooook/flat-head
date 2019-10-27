package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.dal.mappers.LogMapper;
import cn.com.flat.head.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Repository
public class LogDaoImpl implements LogDao {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.addLog(log);
    }
}
