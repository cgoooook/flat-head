package cn.com.flat.head.log;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

import java.util.Date;

/**
 * Created by panzhuowen on 2019/10/27.
 */
public class LoggerBuilder {

    public static Log builder(String opType, boolean optResult, String optContent) {
        String principal;
        try {
            Subject currentUser = SecurityUtils.getSubject();
            principal = (String) currentUser.getPrincipal();
        } catch (Exception ignore) {
            principal = "restOrNone";
        }

        return new Log(opType, principal, new Date(), optResult ? 1 : 0, optContent);
    }

}
