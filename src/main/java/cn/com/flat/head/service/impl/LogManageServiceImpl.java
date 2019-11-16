package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.LogManageDao;
import cn.com.flat.head.pojo.OperateArchiveLog;
import cn.com.flat.head.service.LogManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogManageServiceImpl implements LogManageService {
    @Autowired
    LogManageDao logManageDao;
    @Override
    public List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal) {
        return logManageDao.getOperateArchiveLogs(oal);
    }
}
