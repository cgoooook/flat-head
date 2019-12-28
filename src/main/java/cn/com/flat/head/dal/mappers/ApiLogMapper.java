package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.ApiLog;

import java.util.List;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@RepositoryImpl
public interface ApiLogMapper {

    void  addApiLog(ApiLog apiLog);

    int getApiLogCount();

    List<ApiLog> getApiLogListPage(ApiLog apiLog);

}
