package cn.com.flat.head.controller.log;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.OperateArchiveLog;
import cn.com.flat.head.service.LogManageService;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/log/manage")
public class LogManageController {
    @Autowired
    private LogManageService logManageService;

    @GetMapping
    public String toAdminLog(){
        return "log/adminLog";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<OperateArchiveLog> getOperateArchiveLogs(OperateArchiveLog oal, Pageable pageable){

        return new DataTablesResponse<>(pageable, logManageService.getOperatorLogListPage(oal, pageable));
    }

}
