package cn.com.flat.head.mybatis.paser;


import cn.com.flat.head.mybatis.model.Pageable;

/**
 * 类的描述信息
 *
 * @author panzhuowen
 * @version 1.0.1
 */
public interface SqlParser {

    String getPageSql(String sql, Pageable pageable);

}
