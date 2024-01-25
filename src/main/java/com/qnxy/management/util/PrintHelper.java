package com.qnxy.management.util;

import com.qnxy.management.Command;
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
    public static <E extends Enum<?> & Command> void printCommandInfo(E[] arr, PrintIndentLevel indentLevel) {

        // 计算出打印分割线数量
        final int maxTipsLength = Arrays.stream(arr)
                .map(it -> (it.cmdNum() + it.cmdDesc()).length())
                .max(Integer::compareTo)
                .map(it -> it * 2 + 1)
                .orElse(0);

        printText("\n", PrintIndentLevel.ZERO);
        printLine(maxTipsLength, '=');

        for (E e : arr) {
            printText(
                    String.format("| %s. %s%n", e.cmdNum(), e.cmdDesc()),
                    indentLevel
            );
        }
        printLine(maxTipsLength, '=');
    }

    /**
     * 打印分割线
     *
     * @param lineCount 打印数量
     * @param lineText  分隔线符号
     */
    private static void printLine(int lineCount, char lineText) {
        for (int i = 0; i < lineCount; i++) {
            System.out.print(lineText);
        }
        System.out.println();
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
}
