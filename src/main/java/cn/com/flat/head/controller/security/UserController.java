package cn.com.flat.head.controller.security;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Role;
import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.RoleService;
import cn.com.flat.head.service.UserService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poney on 2019-10-05.
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String userPage() {
        return "security/user";
    }


    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<User> userList(User user, Pageable pageable) {
        List<User> userListPage = userService.getUserListPage(user, pageable);
        return new DataTablesResponse<>(pageable, userListPage);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResponse deleteUser(@PathVariable("id") String id, HttpSession session) {
        boolean b = userService.deleteUserById(id);
        return AjaxResponse.getInstanceByResult(b, session);
    }


    @GetMapping("/roleList")
    @ResponseBody
    public AjaxResponse getRoleList() {
        List<Role> roleListForUser = roleService.getRoleListForUser();
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(roleListForUser);
        return ajaxResponse;
    }

    @PutMapping
    @ResponseBody
    public AjaxResponse adUser(@RequestBody User user, HttpSession httpSession) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier b = userService.addUser(user);
        if (!b.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(b.getMessage());
        }
        return ajaxResponse;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResponse getUser(@PathVariable("id") String id, HttpSession session) {
        User user = userService.getUserById(id);
        List<Role> roleListForUser = roleService.getRoleListForUser();
        Map<String, Object> data = new HashMap<>();
        AjaxResponse ajaxResponse = new AjaxResponse();
        data.put("user", user);
        data.put("roleList", roleListForUser);
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    @PostMapping
    @ResponseBody
    public AjaxResponse updateUser(User user, HttpSession session) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = userService.updateUser(user);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
        }
        return ajaxResponse;
    }
}
