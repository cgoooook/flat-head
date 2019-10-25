package cn.com.flat.head.controller.security;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.UserService;
import cn.com.flat.head.web.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by poney on 2019-10-05.
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
