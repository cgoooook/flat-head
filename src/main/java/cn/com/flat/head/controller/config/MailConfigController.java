package cn.com.flat.head.controller.config;

import cn.com.flat.head.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail/config")
public class MailConfigController {
    @Autowired
    ConfigService configService;


    @GetMapping
    public String returnMailConfig() {
        return "config/mailConfig";
    }






}
