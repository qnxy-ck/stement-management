package com.qnxy.management.util;

import com.qnxy.management.Command;
import com.qnxy.management.data.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * 控制台信息打印助手
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrintHelper {

    /**
     * 打印banner信息
     */
    public static void printBanner() {
        System.out.println("欢迎使用学生信息管理系统");
    }

    /**
     * 命令打印工具
     *
     * @param arr         指定命令数组
     * @param indentLevel 缩进层级
     * @param <E>         限制那个命令集
     */
    public static <E extends Enum<?> & Command> void printCommandList(E[] arr, PrintIndentLevel indentLevel) {

        // 计算出打印分割线数量
        final int maxTipsLength = Arrays.stream(arr)
                .map(it -> (it.cmdVal() + it.cmdDesc()).length())
                .max(Integer::compareTo)
                .map(it -> it * 2 + 5)
                .orElse(0);

        printLine(maxTipsLength, '=', indentLevel);

        for (E e : arr) {
            printText(
                    String.format("| %s. %s%n", e.cmdVal(), e.cmdDesc()),
                    indentLevel
            );
        }
        printLine(maxTipsLength, '=', indentLevel);
    }

    /**
     * 打印分割线
     *
     * @param lineCount 打印数量
     */
    private static void printLine(int lineCount, char lineText, PrintIndentLevel indentLevel) {
        printText((lineText + "").repeat(lineCount) + "\n", indentLevel);
    }

    /**
     * 打印文本信息
     *
     * @param text        文本内容
     * @param indentLevel 打印缩进层级
     */
    public static void printText(String text, PrintIndentLevel indentLevel) {
        for (int i = 0; i < indentLevel.getIndent(); i++) {
            System.out.print(" ");
        }
        System.out.print(text);
    }


    public static void printPageInfo(Page<?> page, PrintIndentLevel indentLevel) {
        final var pageInfo = String.format(
                "* 当前页: %s\t当前页条数: %s\t总条数:%s\t总页数:%s *%n%n",
                page.getCurrentPage(),
                page.getCurrentPageSize(),
                page.getTotal(),
                page.getTotalPage()
        );

        printLine(pageInfo.length() * 2, '-', indentLevel);
        printText(pageInfo, indentLevel);
    }


}
