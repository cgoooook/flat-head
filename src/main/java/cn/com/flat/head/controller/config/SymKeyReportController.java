package cn.com.flat.head.controller.config;

import cn.com.flat.head.service.KeyPairApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by panzhuowen on 2019/12/29.
 */
@Controller
@RequestMapping("/config/symKey")
public class SymKeyReportController {


    @Autowired
    private KeyPairApplyService keyPairApplyService;

    @RequestMapping
    public String symKeyReport(HttpServletRequest request) {
        request.setAttribute("model", keyPairApplyService.getKeyNumber());
        return "config/symKey";
    }

}
