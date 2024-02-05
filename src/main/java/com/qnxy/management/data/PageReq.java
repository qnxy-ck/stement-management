package com.qnxy.management.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页查询参数封装
 *
 * @author Qnxy
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageReq {

    /**
     * 每页默认大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 每页大小最大值
     */
    public static final int DEFAULT_MAX_PAGE_SIZE = 30;

    /**
     * 查询每页展示条数
     */
    private final int pageSize;

    /**
     * 查询指定页数
     */
    private final int currentPage;


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

    public PageReq upPage() {
        return new PageReq(this.pageSize, this.currentPage - 1);
    }

    public PageReq downPage() {
        return new PageReq(this.pageSize, this.currentPage + 1);
    }


    public static PageReq of(int pageSize, int currentPage) {
        return new PageReq(pageSize, currentPage);
    }

    public static PageReq defaultPage() {
        return new PageReq(DEFAULT_PAGE_SIZE, 1);
    }
}
