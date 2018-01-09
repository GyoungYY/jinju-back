package com.aguang.jinjuback.pojo.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 分页pojo类
 * @param <T>
 */
public class PageInfo<T> implements Serializable {

    // 总数
    private int total;

    // 分页数据
    @JSONField(name = "list")
    private List<T> data;

    public PageInfo() {}

    public PageInfo(int total, List<T> data) {
        this.total = total;
        this.data = data;
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