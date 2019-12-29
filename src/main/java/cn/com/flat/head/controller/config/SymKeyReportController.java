package cn.com.flat.head.controller.config;

import cn.com.flat.head.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by panzhuowen on 2019/12/29.
 */
@Controller
@RequestMapping("/config/symKey")
public class SymKeyReportController {

    @Autowired
    private ConfigService configService;

    @RequestMapping
    public String symKeyReport() {
        return "";
    }

}
