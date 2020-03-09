package cn.com.flat.head.controller.log;

import cn.com.flat.head.pojo.OperateArchiveLog;
import cn.com.flat.head.service.LogManageService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/log/archiving")
public class LogArchivingController {
    @Autowired
    private LogManageService logManageService;
    @GetMapping
    public String toArchiving(){
        return "log/logArchiving.html";
    }
    @PostMapping("/archiving")
    @ResponseBody
    public AjaxResponse logArchiving(@RequestBody OperateArchiveLog operateArchiveLog){

        int archiving = logManageService.archiving(operateArchiveLog);

        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        if(archiving>0){
            ajaxResponse.setMsg("ok");
        }else {
            ajaxResponse.setMsg("null");
        }

        return ajaxResponse;
    }

}
