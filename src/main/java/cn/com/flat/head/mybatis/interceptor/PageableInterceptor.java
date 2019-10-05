package cn.com.flat.head.mybatis.interceptor;

import cn.com.flat.head.mybatis.model.Dialect;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.mybatis.paser.impl.AbstractSqlParser;
import cn.com.flat.head.mybatis.paser.impl.MysqlParser;
import cn.com.flat.head.mybatis.paser.impl.OracleParser;
import cn.com.flat.head.mybatis.paser.impl.SqlServerParser;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 *
 * @author panzhuowen
 * @version 1.0.1
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = Constants.SIGNATURE_PREPARE, args = {Connection.class, Integer.class}),
        @Signature(type = ResultSetHandler.class, method = Constants.HANDLE_RESULT_SETS, args = {Statement.class})
})
public class PageableInterceptor implements Interceptor {

    private final static Logger LOGGER = LogManager.getLogger(PageableInterceptor.class);

    private static final ThreadLocal<Pageable> PAGE_THREAD_LOCAL = new ThreadLocal<>();

    private AbstractSqlParser sqlParser = null;

    public static void startPage(Pageable page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    private static void endPage() {
        PAGE_THREAD_LOCAL.remove();
    }

    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Pageable page) {
        String countSql = "select count(1) from (" + sql + ") temp_count";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalResult(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            try {
                if (countStmt != null) {
                    countStmt.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    public Object intercept(Invocation invocation) throws Throwable {
        Pageable page = PAGE_THREAD_LOCAL.get();
        if (page == null) {
            return invocation.proceed();
        }
        if (!(invocation.getTarget() instanceof StatementHandler)) {
            return null;
        }
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        setPageParameter(sql, connection, mappedStatement, boundSql, page);
        String pageSql = sqlParser.getPageSql(sql, page);
        metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
        endPage();
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
    public void setProperties(Properties properties) {
        String dialectValue = (String) properties.get("dialect");
        if (dialectValue == null) {
            throw new IllegalArgumentException("mybatis配置中没有配置需要分页的数据库");
        }
        Dialect getDialect = Dialect.of(dialectValue);
        switch (getDialect) {
            case mysql:
                sqlParser = new MysqlParser();
                break;
            case mssqlserver:
                sqlParser = new SqlServerParser();
                break;
            case oracle:
                sqlParser = new OracleParser();
                break;
        }
    }
}
