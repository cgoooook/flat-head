package cn.com.flat.head.mybatis.paser.impl;


import cn.com.flat.head.mybatis.model.Pageable;

public class SqlServerParser extends AbstractSqlParser {


    @Override
    void getZhSortField(StringBuilder sortSql, String sortField) {
        sortSql.append("CONVERT(CHAR,").append(sortField).append(")");
    }

    @Override
    void getNumSortField(StringBuilder sortSql, String sortField) {
        sortSql.append("CONVERT(BIGINT,").append(sortField).append(")");
    }

    @Override
    String getSortSql(Pageable pageable) {
        if (validateSortField(pageable)) {
            return "";
        } else {
            StringBuilder sortSql = new StringBuilder("SELECT ROW_NUMBER() OVER ( ORDER BY ");
            String[] sortFields = pageable.getSortField().split(",");
            String[] sortFieldTypes = pageable.getSortFieldType() == null ? new String[0] : pageable.getSortFieldType().split(",");
            String[] sortOrders = getSortOrderArray(pageable);
            appendSort(sortSql, sortFields, sortFieldTypes, sortOrders);
            return sortSql.deleteCharAt(sortSql.length() - 1).toString() + " ) as rownum, * ";
        }
    }


    @Override
    public String getPageSql(String sql, Pageable pageable) {
        return "select * from (select * from (" + getSortSql(pageable) + " from (" + sql + ") as temp ) as" +
                " tempc) as tempb where rownum BETWEEN " + (getCurrentResult(pageable) + 1) + " AND " + getEndResult(pageable);
    }
}
