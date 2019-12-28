package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.ApiLog;

import java.util.List;

/**
 * Created by panzhuowen on 2019/12/28.
 */
public interface ApiLogDao {

    List<ApiLog> getApiLogListPage(ApiLog apiLog);

}
