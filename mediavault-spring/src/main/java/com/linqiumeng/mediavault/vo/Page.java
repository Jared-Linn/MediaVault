package com.linqiumeng.mediavault.vo;

import lombok.Data;

import com.linqiumeng.mediavault.entity.User;
import java.util.List;

public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int total;
    private List<T> tableData;

    public Page(int pageNum, int pageSize, int total, List<T> tableData) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.tableData = tableData;
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

    public List<T> getTableData() {
        return tableData;
    }

    public void setTableData(List<T> tableData) {
        this.tableData = tableData;
    }
}
