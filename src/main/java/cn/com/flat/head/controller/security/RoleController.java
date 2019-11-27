package cn.com.flat.head.controller.security;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.Role;
import cn.com.flat.head.service.MenuService;
import cn.com.flat.head.service.RoleService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by panzhuowen on 2019/11/27.
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping
    public String rolePage() {
        return "security/role";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Role> roleList(Pageable pageable, Role role) {
        return new DataTablesResponse<>(pageable, roleService.getRoleListPage(pageable, role));
    }

    @GetMapping("/getMenuList")
    @ResponseBody
    public AjaxResponse getMenuList() {
        AjaxResponse ajaxResponse = new AjaxResponse();
//        menuService.
        return ajaxResponse;
    }

}
