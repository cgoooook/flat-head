package cn.com.flat.head.service;

import cn.com.flat.head.pojo.OperateArchiveLog;

import java.util.List;

public interface LogManageService {
    List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal);
}
