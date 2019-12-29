package cn.com.flat.head.controller.home;

import cn.com.flat.head.pojo.*;
import cn.com.flat.head.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeInformationController {
    @Autowired
    KmsDeviceKeyPairService kmsDeviceKeyPairService;
    @Autowired
    OrgService orgService;
    @Autowired
    DevService devService;
    @Autowired
    KeyService keyService;
    @Autowired
    LogManageService logManageService;
    @Autowired
    ApiLogService apiLogService;
    @GetMapping("/info")
    @ResponseBody
    public HomePageInfomation getHomePageInfomation(){
        DeviceKeyPair deviceKeyPair = new DeviceKeyPair();
        deviceKeyPair.setAlg("SM2");
        deviceKeyPair.setStatus(1);
        //kms_device_key_pair  sm2在用 alg = sm2 status =1
        int deviceKeyPairNum =  kmsDeviceKeyPairService.getKeyPair(deviceKeyPair);
        HomePageInfomation homePageInfomation = new HomePageInfomation();
        HomeSm2Info homeSm2Info = new HomeSm2Info();
        homeSm2Info.setInUseNum(deviceKeyPairNum);
        int sm2SpareNum = kmsDeviceKeyPairService.getSpareNumByType("SM2");
        homeSm2Info.setSpareNum(sm2SpareNum);
        homePageInfomation.setHomeSm2Info(homeSm2Info);
        int orgNum = orgService.getOrgNum();
        HomeOrganizationAndDeviceInfo homeOrganizationAndDeviceInfo = new HomeOrganizationAndDeviceInfo();
        homeOrganizationAndDeviceInfo.setOrganizationNum(orgNum);
        int deviceNum = devService.getDeviceNum();
        homeOrganizationAndDeviceInfo.setDeviceNum(deviceNum);
        homePageInfomation.setHomeOrganizationAndDeviceInfo(homeOrganizationAndDeviceInfo);
        HomeTemplateInfo homeTemplateInfo=new HomeTemplateInfo();
        HashMap<String,Integer> map =new HashMap<>();
        //获取模板列表
        List<KeyTemplate> keyGenTemplate = keyService.getKeyGenTemplate();
        for (KeyTemplate temp:keyGenTemplate) {
            String templateId = temp.getTemplateId();
            int num = keyService.geyKeyCountByTemplateId(templateId);
            map.put(temp.getTemplateName(),num);
        }
        homeTemplateInfo.setMap(map);
        homePageInfomation.setHomeTemplateInfo(homeTemplateInfo);
        HomeLogInfo homeLogInfo = new HomeLogInfo();
        int operatorLogNum = logManageService.allLog();
        int apiLog = apiLogService.getApiLogCount();
        homeLogInfo.setManagerLogNum(operatorLogNum);
        homeLogInfo.setServiceLogNum(apiLog);
        homePageInfomation.setHomeLogInfo(homeLogInfo);
        return homePageInfomation;
    }
}
