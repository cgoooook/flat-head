package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.ApiLog;

import java.util.List;

/**
 * Created by panzhuowen on 2019/12/28.
 */
public interface ApiLogService {

    List<ApiLog> getApiLogListPage(Pageable pageable, ApiLog apiLog);

    void  addApiLog(ApiLog apiLog);

    int getApiLogCount();
}
