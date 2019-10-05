package cn.com.flat.head.web;

import cn.com.flat.head.mybatis.model.Pageable;

import java.util.List;

/**
 * Created by poney on 2019-10-05.
 */
public class DataTablesResponse<T> {

    private Pageable pageable;

    private List<T> resultList;

    public DataTablesResponse(Pageable pageable, List<T> resultList) {
        this.pageable = pageable;
        this.resultList = resultList;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
