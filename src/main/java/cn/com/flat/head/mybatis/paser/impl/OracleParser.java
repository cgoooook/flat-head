package cn.com.flat.head.mybatis.paser.impl;


import cn.com.flat.head.mybatis.model.Pageable;

public class OracleParser extends AbstractSqlParser  {

    @Override
    void getZhSortField(StringBuilder sortSql, String sortField) {
        sortSql.append(" ").append(sortField).append(" ");
    }

    @Override
    void getNumSortField(StringBuilder sortSql, String sortField) {
        sortSql.append(" ").append(sortField).append(" ");
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

    @Override
    public String getPageSql(String sql, Pageable pageable) {
        return "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql + getSortSql(pageable) + ") A) WHERE RN BETWEEN " + (getCurrentResult(pageable) + 1) + " AND " + getEndResult(pageable);
    }
}
