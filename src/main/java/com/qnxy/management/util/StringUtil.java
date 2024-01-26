package com.qnxy.management.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 字符串处理校验相关工具
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

    public static boolean noBlank(String text) {
        return text != null && !text.isBlank();
    }

    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

}
