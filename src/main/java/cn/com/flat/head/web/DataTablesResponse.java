package cn.com.flat.head.web;

import cn.com.flat.head.mybatis.model.Pageable;

import java.util.List;

/**
 * Created by poney on 2019-10-05.
 */
public class DataTablesResponse<T> {

    private Pageable page;

    private List<T> data;

    public DataTablesResponse(Pageable pageable, List<T> resultList) {
        this.page = pageable;
        this.data = resultList;
    }

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
