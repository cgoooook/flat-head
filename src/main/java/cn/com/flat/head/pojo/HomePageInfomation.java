package cn.com.flat.head.pojo;

import java.util.List;

public class HomePageInfomation {
    //sm2备用密钥数量，sm2在用密钥数量
    private HomeSm2Info homeSm2Info;
    //当前账号信息，上次登录时间
    private HomeSignIn homeSignIn;
    //当前机构数量， 当前注册设备数量
    private HomeOrganizationAndDeviceInfo homeOrganizationAndDeviceInfo;
    //key 模板 v 数量
    private HomeTemplateInfo homeTemplateInfo;
    //管理日志记录数&服务日志记录数
    private HomeLogInfo homeLogInfo;

    private List<HomeTemplateInfo> templateInfoList;

    public HomeSm2Info getHomeSm2Info() {
        return homeSm2Info;
    }

    public void setHomeSm2Info(HomeSm2Info homeSm2Info) {
        this.homeSm2Info = homeSm2Info;
    }

    public HomeSignIn getHomeSignIn() {
        return homeSignIn;
    }

    public void setHomeSignIn(HomeSignIn homeSignIn) {
        this.homeSignIn = homeSignIn;
    }

    public HomeOrganizationAndDeviceInfo getHomeOrganizationAndDeviceInfo() {
        return homeOrganizationAndDeviceInfo;
    }

    public void setHomeOrganizationAndDeviceInfo(HomeOrganizationAndDeviceInfo homeOrganizationAndDeviceInfo) {
        this.homeOrganizationAndDeviceInfo = homeOrganizationAndDeviceInfo;
    }

    public HomeTemplateInfo getHomeTemplateInfo() {
        return homeTemplateInfo;
    }

    public void setHomeTemplateInfo(HomeTemplateInfo homeTemplateInfo) {
        this.homeTemplateInfo = homeTemplateInfo;
    }

    public HomeLogInfo getHomeLogInfo() {
        return homeLogInfo;
    }

    public void setHomeLogInfo(HomeLogInfo homeLogInfo) {
        this.homeLogInfo = homeLogInfo;
    }

    public List<HomeTemplateInfo> getTemplateInfoList() {
        return templateInfoList;
    }

    public void setTemplateInfoList(List<HomeTemplateInfo> templateInfoList) {
        this.templateInfoList = templateInfoList;
    }
}
