package cn.com.flat.head.controller.security;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Menu;
import cn.com.flat.head.pojo.Role;
import cn.com.flat.head.service.MenuService;
import cn.com.flat.head.service.RoleService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Menu> menuList = menuService.getMenuList();
        ajaxResponse.setData(menuList);
        return ajaxResponse;
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse addRole(@RequestBody Role role) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = roleService.addRole(role);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }

    @GetMapping("/{roleId}")
    @ResponseBody
    public AjaxResponse getRoleById(@PathVariable("roleId") String roleId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        Map<String, Object> data = new HashMap<>();
        Role roleById = roleService.getRoleById(roleId);
        data.put("role", roleById);
        List<Menu> menuList = menuService.getMenuList();
        dealMenuCheck(menuList, roleById);
        data.put("menu", menuList);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    @PostMapping("/updateRole")
    @ResponseBody
    public AjaxResponse updateRole(@RequestBody Role role) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = roleService.updateRole(role);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }

    @DeleteMapping("/{roleId}")
    @ResponseBody
    public AjaxResponse deleteRole(@PathVariable("roleId") String roleId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = roleService.deleteRoleById(roleId);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }

    private void dealMenuCheck(List<Menu> menuList, Role role) {
        menuList.forEach(menu -> {
            if (role.getPermTokens().contains(menu.getPermToken())) {
                menu.setChecked(true);
            }
        });
    }

}
