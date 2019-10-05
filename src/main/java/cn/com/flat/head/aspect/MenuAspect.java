package cn.com.flat.head.aspect;

import cn.com.flat.head.cache.MenuCache;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by poney on 2019-10-05.
 */
@Component
@Aspect
public class MenuAspect {

    @Autowired
    private MenuCache menuCache;

    @Pointcut("execution(* cn.com.flat.head.controller.*.*Controller.*(..))")
    public void menuPointCut() {
    }

    @AfterReturning(value = "menuPointCut()",returning = "rov")
    public void afterReturning(Object rov) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        addRequestAttr(request);
    }

    private void addRequestAttr(HttpServletRequest request) {
        String menuId = request.getParameter("menuId");
        if (menuId != null) {
            request.setAttribute("menu", menuCache.getMenu(menuId));
        }
    }

}
