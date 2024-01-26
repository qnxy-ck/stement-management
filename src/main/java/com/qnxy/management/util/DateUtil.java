package com.qnxy.management.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式化工具
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 日期类型转字符串
     * 日期格式: yyyy-MM-dd
     *
     * @param date 日期
     * @return .
     */
    public static String localDateToText(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * 日期时间类型转字符串
     * 日期格式: yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime 日期时间
     * @return .
     */
    public static String localDateToText(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 字符串转日期
     * 日期格式: yyyy-MM-dd
     *
     * @param text 字符串
     * @return .
     */
    public static LocalDate textToLocalDate(String text) {
        return LocalDate.parse(text, DATE_FORMATTER);
    }

}
