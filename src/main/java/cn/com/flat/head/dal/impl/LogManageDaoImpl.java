package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.LogManageDao;
import cn.com.flat.head.dal.mappers.LogManageMapper;
import cn.com.flat.head.pojo.OperateArchiveLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class LogManageDaoImpl implements LogManageDao {
    @Autowired
    private LogManageMapper logManageMapper;
    @Override
    public List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal) {
        return logManageMapper.getOperateArchiveLogs(oal);
    }

    @Override
    public int auditLog(String logId) {
        return logManageMapper.auditLog(logId);
    }



    @Override
    public void insertOperateArchiveLogs(List<OperateArchiveLog> archiving) {
        logManageMapper.insertOperateArchiveLogs(archiving);
    }

    @Override
    public void deleteOperatorLog(List<OperateArchiveLog> archiving) {
        logManageMapper.deleteOperatorLog(archiving);
    }


}
