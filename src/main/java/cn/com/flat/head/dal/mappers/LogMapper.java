package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.log.Log;
import cn.com.flat.head.mybatis.RepositoryImpl;

/**
 * Created by panzhuowen on 2019/10/27.
 */
@RepositoryImpl
public interface LogMapper {

    void addLog(Log log);

}
