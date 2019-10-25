package cn.com.flat.head.web;

import cn.com.flat.head.mybatis.model.Pageable;

import java.util.List;

/**
 * Created by poney on 2019-10-05.
 */
public class DataTablesResponse<T> {

    private int recordsTotal;
    private int recordsFiltered;
    private int draw;

    private List<T> data;

    public DataTablesResponse(Pageable pageable, List<T> resultList) {
        if (pageable != null) {
            this.recordsTotal = pageable.getTotalResult();
            this.recordsFiltered = pageable.getTotalResult();
            this.draw = pageable.getDraw();
        }
        this.data = resultList;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
