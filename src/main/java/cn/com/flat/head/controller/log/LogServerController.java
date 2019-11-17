package cn.com.flat.head.controller.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log/server")
public class LogServerController {
    @GetMapping
    public String toLogServer(){
        return "log/logServer.html";
    }

}
