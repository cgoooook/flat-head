package cn.com.flat.head.controller;

import cn.com.flat.head.pojo.User;
import cn.com.flat.head.service.UserService;
import cn.com.flat.head.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by poney on 2019-10-04.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String login() {

        return "login";
    }

    @PostMapping
    public String loginPost(User user, RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), "1".equals(user.getRemember()));
        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("login.usernameOrPasswordWrong", null, locale));
            return "redirect:/login";
        }
        if (currentUser.isAuthenticated()) {
            sessionHandle(request, user);
//            addLogInfo(user, request);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("login.usernameOrPasswordWrong", null, locale));
            return "redirect:/login";
        }
    }

    private void sessionHandle(HttpServletRequest request, User user) {
        User userByUsername = userService.getUserByUsername(user.getUsername());
        SessionUtil.setUserToSession(request, userByUsername);
    }

}
