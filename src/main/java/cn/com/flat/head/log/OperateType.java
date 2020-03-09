package cn.com.flat.head.log;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public interface OperateType {

    /**
     * 登陆
     */
    String login = "login";
    String logout = "logout";

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

    /**
     * 添加密钥
     */
    String addKey = "addKey";

    /**
     * 更新密钥
     */
    String updateKey = "updateKey";

    /**
     * 归档密钥
     */
    String archiveKey = "archiveKey";

    /**
     * 启用密钥
     */
    String enableKey = "enableKey";

    /**
     * 禁用密钥
     */
    String disableKey = "disableKey";

    /**
     * 删除密钥
     */
    String deleteKey = "deleteKey";

    /**
     * 导入密钥
     */
    String importKey = "importKey";

    /**
     * 添加密钥集
     */
    String addCollection = "addCollection";

    /**
     * 更新密钥集
     */
    String updateCollection = "updateCollection";

    /**
     * 删除密钥集
     */
    String deleteCollection = "deleteCollection";

    /**
     * 添加设备
     */
    String addDevice = "addDevice";

    /**
     * 更新设备
     */
    String updateDevice = "updateDevice";

    /**
     * 删除设备
     */
    String deleteDevice = "deleteDevice";

    /**
     * 数据库配置
     */
    String jdbcConfig = "jdbcConfig";

    /**
     * 日志配置
     */
    String logConfig = "logConfig";

    /**
     * ui配置
     */
    String uiConfig = "uiConfig";

    /**
     * 邮件配置
     */
    String mailConfig = "mailConfig";

    /**
     * 添加任务
     */
    String addTeak = "addTask";

    /**
     * 更新任务
     */
    String updateTask = "updateTask";

    /**
     * 删除任务
     */
    String deleteTask = "deleteTask";

}

