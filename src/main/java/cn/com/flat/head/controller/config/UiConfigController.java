package cn.com.flat.head.controller.config;

import cn.com.flat.head.pojo.Jdbc;
import cn.com.flat.head.pojo.SysLogo;
import cn.com.flat.head.service.ConfigService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/ui/config")
public class UiConfigController {
    @Autowired
    ConfigService configService;

    @GetMapping
    public String returnUiConfig() {
        return "config/uiConfig";
    }


    @PostMapping("/upload")
    @ResponseBody
    public AjaxResponse editUiConfig(MultipartFile logo ,String copyright, HttpSession httpSession){


        configService.editUiConfig(logo,copyright);

//        logo.getOriginalFilename();
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        return ajaxResponse;
    }

    @GetMapping("/info")
    @ResponseBody
    public AjaxResponse getUiInfo(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        SysLogo  logo = configService.getUiInfo();
        if(logo!=null){
            ajaxResponse.setData(logo);
            ajaxResponse.setReturnState(ReturnState.OK);
        }else {
            ajaxResponse.setReturnState(ReturnState.ERROR);
        }
        return   ajaxResponse;
    }
}
