package cn.com.flat.head.mybatis.paser.impl;


import cn.com.flat.head.mybatis.model.Pageable;

/**
 * 类的描述信息
 *
 * @author panzhuowen
 * @version 1.0.1
 */
public class MysqlParser extends AbstractSqlParser {

    @Override
    public String getPageSql(String sql, Pageable pageable) {
        return sql + this.getSortSql(pageable) + " limit " + getCurrentResult(pageable) + "," + pageable.getPageSize();
    }

    @Override
    void getZhSortField(StringBuilder sortSql, String sortField) {
        sortSql.append("CONVERT(").append(sortField).append(" USING gbk)");
    }

    @Override
    void getNumSortField(StringBuilder sortSql, String sortField) {
        sortSql.append("CONVERT(").append(sortField).append(",SIGNED)");
    }

    @Override
    String getSortSql(Pageable pageable) {
        if (validateSortField(pageable)) {
            return "";
        } else {
            StringBuilder sortSql = new StringBuilder(" order by ");
            String[] sortFields = pageable.getSortField().split(",");
            String[] sortFieldTypes = pageable.getSortFieldType() == null ? new String[0] : pageable.getSortFieldType().split(",");
            String[] sortOrders = getSortOrderArray(pageable);

            appendSort(sortSql, sortFields, sortFieldTypes, sortOrders);

            return sortSql.deleteCharAt(sortSql.length() - 1).toString();
        }
    }
}
