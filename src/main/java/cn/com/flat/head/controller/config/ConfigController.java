package cn.com.flat.head.controller.config;

import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Jdbc;
import cn.com.flat.head.service.ConfigService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/db/config")
public class ConfigController {
    @Autowired
    ConfigService configService;


    @GetMapping
    public String returnDbConfig() {
        return "config/dbConfig";
    }


    @PostMapping("/jdbc")
    @ResponseBody
    public AjaxResponse  updateJdbcConfig(@RequestBody Jdbc jdbc, HttpSession httpSession){
        BooleanCarrier booleanCarrier = configService.updateJdbcConfig(jdbc);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }

    @GetMapping("/jdbc")
    @ResponseBody
    public Jdbc getJdbcConfig(){
        Jdbc  jdbc = configService.getJdbcConfig();
        return jdbc;
    }

}
