package cn.com.flat.head.util;

import cn.com.flat.head.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by poney on 2019-10-05.
 */
public class SessionUtil {

    private static final String USER = "user";


    public static void setUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(USER, user);
    }


}
