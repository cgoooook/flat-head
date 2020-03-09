package cn.com.flat.head.controller.config;

import cn.com.flat.head.pojo.Mail;
import cn.com.flat.head.service.ConfigService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mail/config")
public class MailConfigController {
    @Autowired
    ConfigService configService;


    @GetMapping
    public String returnMailConfig() {
        return "config/mailConfig";
    }


    @PostMapping("/test")
    @ResponseBody
    public AjaxResponse testMail(@RequestBody Mail mail){
        boolean b = configService.sendMail(mail);
        AjaxResponse ajaxResponse = new AjaxResponse();
        if(b){
            ajaxResponse.setReturnState(ReturnState.OK);
        }else {
            ajaxResponse.setReturnState(ReturnState.ERROR);
        }
        return ajaxResponse;
        }

    @PostMapping("/save")
    @ResponseBody
    public AjaxResponse saveMailConfig(@RequestBody Mail mail){
        configService.saveMail(mail);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        return ajaxResponse;
    }
    @GetMapping("/mailConfig")
    @ResponseBody
    public AjaxResponse getMailConfig(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        Mail mail = configService.getMail();
        if(mail==null){
            ajaxResponse.setReturnState(ReturnState.ERROR);

        }else {
            ajaxResponse.setData(mail);
            ajaxResponse.setReturnState(ReturnState.OK);
        }
        return ajaxResponse;
    }




}
