package cn.com.flat.head.mybatis;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by poney on 2019-09-30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface RepositoryImpl {
}
