package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.OperateArchiveLog;

import java.util.List;

public interface LogManageService {

    List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal);

    List<OperateArchiveLog> getOperatorLogListPage(OperateArchiveLog oal, Pageable pageable);

    boolean auditLog(String logId);
}
