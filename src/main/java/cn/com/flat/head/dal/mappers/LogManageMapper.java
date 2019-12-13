package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.OperateArchiveLog;

import java.util.List;
@RepositoryImpl
public interface LogManageMapper {
    List<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal) ;

    int auditLog(String logId);

    void archiving(OperateArchiveLog operateArchiveLog);


}
