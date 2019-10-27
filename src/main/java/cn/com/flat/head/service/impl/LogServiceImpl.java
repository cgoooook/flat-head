package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.log.Log;
import cn.com.flat.head.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@Service
public class LogServiceImpl implements LogService {


    @Autowired
    private LogDao logDao;

    @Override
    public void addLog(Log log) {
        logDao.addLog(log);
    }
}
