package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.ApiLogDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.ApiLog;
import cn.com.flat.head.service.ApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@Service
public class ApiLogServiceImpl implements ApiLogService {

    @Autowired
    private ApiLogDao apiLogDao;

    @Override
    public List<ApiLog> getApiLogListPage(Pageable pageable, ApiLog apiLog) {
        PageableInterceptor.startPage(pageable);
        return apiLogDao.getApiLogListPage(apiLog);
    }

    @Override
    public void addApiLog(ApiLog apiLog) {
        apiLogDao.addApiLog(apiLog);
    }
}
