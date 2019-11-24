package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.OperateArchiveLog;

import java.util.List;

public interface LogManageDao {

    List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal);

    int auditLog(String logId);
}
