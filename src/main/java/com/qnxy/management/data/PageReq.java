package com.qnxy.management.data;

import lombok.Data;

/**
 * 分页查询参数封装
 *
 * @author Qnxy
 */
@Data
public class PageReq {

    /**
     * 每页默认大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 每页大小最大值
     */
    private static final int DEFAULT_MAX_PAGE_SIZE = 30;

    /**
     * 查询每页展示条数
     */
    private int pageSize;

    /**
     * 查询指定页数
     */
    private int currentPage;


    public int getPageSize() {
        // 如果没页数小于等于0
        // 则使用默认页数大小
        if (pageSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        // 限制每页最大条数
        return Math.min(pageSize, DEFAULT_MAX_PAGE_SIZE);
    }

    public int getCurrentPage() {
        // 最小页数为 1
        return Math.max(currentPage, 1);
    }
}
