package cn.com.flat.head.log;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface OperateType {

    /**
     * 登陆
     */
    String login = "login";

    /**
     * 添加用户
     */
    String addUser = "addUser";

    /**
     * 更新用户
     */
    String updateUser = "updateUser";

    /**
     * 删除用户
     */
    String deleteUser = "deleteUser";

    /**
     * 添加机构
     */
    String addOrg = "addOrg";
    /**
     * 更新机构
     */
    String updateOrg = "updateOrg";
    /**
     * 删除机构
     */
    String deleteOrg = "deleteOrg";
    /**
     * 导出机构数据
     */
    String exportOrg = "exportOrg";

    /**
     * 添加模版
     */
    String addTemplate = "addTemplate";

    /**
     * 更新模版
     */
    String updateTemplate = "updateTemplate";

    /**
     * 删除模版
     */
    String deleteTemplate = "deleteTemplate";


}
