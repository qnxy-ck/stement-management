package com.qnxy.management.data;

import lombok.Data;

import java.util.Collection;

/**
 * 分页数据信息包装
 * 
 * @author Qnxy
 */
@Data
public class Page<T> {

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 总条数
     */
    private int total;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 实际数据信息
     */
    private Collection<T> records;
}
