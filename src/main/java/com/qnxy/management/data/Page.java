package com.qnxy.management.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * 分页数据信息包装
 *
 * @author Qnxy
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Page<T> {

    /**
     * 当前页
     */
    private final int currentPage;

    /**
     * 当前页数量
     */
    private final int currentPageSize;

    /**
     * 总条数
     */
    private final int total;

    /**
     * 总页数
     */
    private final int totalPage;

    /**
     * 实际数据信息
     */
    private final Collection<T> records;

    public static <T> Page<T> of(Collection<T> records, int total, PageReq pageReq) {

        int p = total / pageReq.getPageSize();
        if (total % pageReq.getPageSize() != 0) {
            p += 1;
        }

        int currentPageSize = records == null || records.isEmpty() ? 0 : records.size();

        return new Page<>(pageReq.getCurrentPage(), currentPageSize, total, p, records);
    }

}
