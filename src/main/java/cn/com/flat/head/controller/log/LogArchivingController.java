package cn.com.flat.head.controller.log;

import cn.com.flat.head.pojo.OperateArchiveLog;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/log/archiving")
public class LogArchivingController {
    @GetMapping
    public String toArchiving(){
        return "log/logArchiving.html";
    }
    @PostMapping("/archiving")
    public AjaxResponse logArchiving(@RequestBody OperateArchiveLog operateArchiveLog){
        //TODO
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);



        return ajaxResponse;
    }

}
