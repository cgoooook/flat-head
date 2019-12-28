package cn.com.flat.head.controller.log;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.ApiLog;
import cn.com.flat.head.service.ApiLogService;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by panzhuowen on 2019/12/28.
 */
@Controller
@RequestMapping("/log/apiLog")
public class ApiLogController {

    @Autowired
    private ApiLogService apiLogService;


    @RequestMapping
    public String apiLog() {
        return "log/apiLog";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/list")
    @ResponseBody
    public DataTablesResponse<ApiLog> getApiLogListPage(Pageable pageable, ApiLog apiLog) {
        return new DataTablesResponse<>(pageable, apiLogService.getApiLogListPage(pageable, apiLog));
    }

}
