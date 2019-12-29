package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.ApiLogDao;
import cn.com.flat.head.dal.mappers.ApiLogMapper;
import cn.com.flat.head.pojo.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@Repository
public class ApiLogDaoImpl implements ApiLogDao {

    @Autowired
    private ApiLogMapper apiLogMapper;


    @Override
    public List<ApiLog> getApiLogListPage(ApiLog apiLog) {
        return apiLogMapper.getApiLogListPage(apiLog);
    }

    @Override
    public void addApiLog(ApiLog apiLog) {
        apiLogMapper.addApiLog(apiLog);
    }

    @Override
    public int getApiLogCount() {
        return apiLogMapper.getApiLogCount();
    }
}
