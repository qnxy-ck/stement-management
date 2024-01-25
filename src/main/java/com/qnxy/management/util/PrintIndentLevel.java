package com.qnxy.management.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 打印的缩进层级定义
 *
 * @author Qnxy
 */
@Getter
@RequiredArgsConstructor
public enum PrintIndentLevel {

    ZERO(0),
    ONE(4),
    TWO(8),
    ;

    private final int indent;
}
