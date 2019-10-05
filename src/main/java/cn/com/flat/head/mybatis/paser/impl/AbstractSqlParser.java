package cn.com.flat.head.mybatis.paser.impl;


import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.mybatis.paser.SqlParser;
import org.apache.commons.lang3.StringUtils;

/**
 * 类的描述信息
 *
 * @author panzhuowen
 * @version 1.0.1
 */
public abstract class AbstractSqlParser implements SqlParser {

    private static final int DEFAULT_PAGE_NO = 1;

    private static final int NO_RESULTS = 0;

    abstract void getZhSortField(StringBuilder sortSql, String sortField);

    abstract void getNumSortField(StringBuilder sortSql, String sortField);

    private int getTotalPage(Pageable pageable) {
        return pageable.getTotalResult() % pageable.getPageSize() == NO_RESULTS ? pageable.getTotalResult() / pageable.getPageSize() : pageable.getTotalResult() / pageable.getPageSize() + DEFAULT_PAGE_NO;
    }

    private int getReasonablePageNo(Pageable pageable) {
        if (pageable.getPageNo() <= NO_RESULTS) {
            pageable.setPageNo(DEFAULT_PAGE_NO);
        }

        int totalPage = getTotalPage(pageable);
        if (pageable.getPageNo() > totalPage) {
            pageable.setPageNo(totalPage);
        }

        return pageable.getPageNo();
    }

    int getCurrentResult(Pageable pageable) {
        int currentResult = (this.getReasonablePageNo(pageable) - 1) * pageable.getPageSize();
        return currentResult >= 0 ? currentResult : 0;
    }

    int getEndResult(Pageable pageable) {
        int currentResult = (this.getReasonablePageNo(pageable)) * pageable.getPageSize();
        return currentResult >= 0 ? currentResult : 1;
    }

    abstract String getSortSql(Pageable pageable);

    void appendSortField(StringBuilder sortSql, String sortField, String sortFieldType) {
        if (sortFieldType != null && !sortFieldType.equals("")) {
            String sortFieldTypeLowerCase = sortFieldType.toLowerCase();
            switch (sortFieldTypeLowerCase) {
                case "zh":
                    this.getZhSortField(sortSql, sortField);
                    break;
                case "num":
                    this.getNumSortField(sortSql, sortField);
                    break;
            }
        } else {
            sortSql.append(sortField);
        }
    }

    String[] getSortOrderArray(Pageable pageable) {
        String[] sortOrders;
        if (StringUtils.isEmpty(pageable.getSortOrder())) {
            sortOrders = null;
        } else {
            if (pageable.getSortOrder().contains(" ")) {
                throw new IllegalArgumentException("sortOrder不能包含空格");
            }

            sortOrders = pageable.getSortOrder().split(",");
        }

        return sortOrders;
    }

    boolean validateSortField(Pageable pageable) {
        if (StringUtils.isEmpty(pageable.getSortField())) {
            return true;
        } else if (pageable.getSortField().contains(" ")) {
            throw new IllegalArgumentException("sort field cannot contains blank space");
        } else {
            return false;
        }
    }

    void appendSort(StringBuilder sortSql, String[] sortFields, String[] sortFieldTypes, String[] sortOrders) {
        for (int i = 0; i < sortFields.length; ++i) {
            String sortField = sortFields[i];
            if (StringUtils.isEmpty(sortField)) {
                break;
            }

            appendSortField(sortSql, sortField, i >= sortFieldTypes.length ? null : sortFieldTypes[i]);
            if (sortOrders != null) {
                sortSql.append(" ").append(sortOrders[i]);
            }

            sortSql.append(",");
        }
    }

}
