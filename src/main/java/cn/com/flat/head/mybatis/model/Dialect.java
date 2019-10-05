package cn.com.flat.head.mybatis.model;

import java.util.Arrays;
import java.util.List;

/**
 * 类的描述信息
 *
 * @author panzhuowen
 * @version 1.0.1
 */
public enum Dialect {
    mysql, oracle, mssqlserver;

    public static Dialect of(String dialect) {
        try {
            return valueOf(dialect);
        } catch (IllegalArgumentException e) {
            final String[] dialects = {null};
            List<Dialect> dialectList = Arrays.asList(values());
            dialectList.forEach(d -> {
                if (null == dialects[0]) {
                    dialects[0] = d.toString();
                } else {
                    dialects[0] = dialects[0] + ',' + d.toString();
                }
            });
            throw new IllegalArgumentException("Mybatis分页插件dialect参数值错误，可选值为[" + dialects[0] + "]");
        }
    }
}
