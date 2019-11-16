package cn.com.flat.head.controller.config;

import cn.com.flat.head.pojo.LogConfig;
import cn.com.flat.head.pojo.LogConfigBo;
import cn.com.flat.head.service.ConfigService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/log/config")
public class LogConfigController {
    @Autowired
    ConfigService configService;

    @GetMapping
    public String returnLogConfig() {
        return "config/logConfig";
    }

    @GetMapping("/config")
    @ResponseBody
    public Map getLogConfig() {
        List<LogConfig> config = configService.getLogConfig();
        Map<String, String> map = new HashMap();
        for (LogConfig logConfg : config) {
            if ("log_level".equalsIgnoreCase(logConfg.getConfigName())) {
                map.put("log_level", logConfg.getConfigValue());
            } else if ("log_days".equalsIgnoreCase(logConfg.getConfigName())) {
                map.put("log_days", logConfg.getConfigValue());
            }
        }
        return map;
    }

    @PostMapping("/config")
    @ResponseBody
    public AjaxResponse editLogConfig(@RequestBody LogConfigBo bo, HttpSession httpSession) {
        configService.editLogLevel(bo.getLogLevel());
        configService.editLogDays(bo.getLogDays());
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        return ajaxResponse;
    }
}
