package com.linqiumeng.mediavault.vo;

import lombok.Data;

import com.linqiumeng.mediavault.entity.User;
import java.util.List;

public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int total;
    private List<T> data;

    public Page(int pageNum, int pageSize, int total, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
