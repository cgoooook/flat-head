package cn.com.flat.head.controller.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log/archiving")
public class LogArchivingController {
    @GetMapping
    public String toArchiving(){
        return "log/logArchiving.html";
    }

}
