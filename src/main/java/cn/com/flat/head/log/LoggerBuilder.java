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
        Subject currentUser = SecurityUtils.getSubject();
        String principal = (String) currentUser.getPrincipal();
        return new Log(opType, principal, new Date(), optResult ? 1 : 0, optContent);
    }

}
