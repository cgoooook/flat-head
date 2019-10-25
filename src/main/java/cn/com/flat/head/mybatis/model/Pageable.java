package cn.com.flat.head.mybatis.model;

import java.util.Map;

/**
 * 类的描述信息
 *
 * @author panzhuowen
 * @version 1.0.1
 */
public class Pageable {

    private static final int DEFAULT_PAGE_SIZE = 30;
    private static final int DEFAULT_PAGE_NO = 1;
    private int pageSize;
    private int totalResult;
    private int pageNo;
    private int totalPage;
    private String sortField;
    private String sortOrder;
    private String sortFieldType;
    private String[] sumColumns;
    private int draw;
    private Map<String, String> sumResult;

    public Pageable() {
        pageNo = DEFAULT_PAGE_NO;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pageable(int pageSize, int pageNo) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortFieldType() {
        return sortFieldType;
    }

    public void setSortFieldType(String sortFieldType) {
        this.sortFieldType = sortFieldType;
    }

    public String[] getSumColumns() {
        return sumColumns;
    }

    public void setSumColumns(String[] sumColumns) {
        this.sumColumns = sumColumns;
    }

    public Map<String, String> getSumResult() {
        return sumResult;
    }

    public void setSumResult(Map<String, String> sumResult) {
        this.sumResult = sumResult;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
