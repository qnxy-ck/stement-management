package com.qnxy.management.util;

import com.qnxy.management.exceptions.ReadCommandMappingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;

import static com.qnxy.management.util.PrintHelper.printText;

/**
 * 读取命令行信息工具类
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReadCommandUtil {
    private static final Scanner SCANNER = new Scanner(System.in);


    /**
     * 等待并获取用户输入
     * 将输入结果进行转换后返回
     *
     * @param cmdTips         等待用户输入提示信息
     * @param indentLevel     提示信息打印层级
     * @param readTextMapping 转换函数
     * @param <T>             .
     * @return .
     */
    public static <T> T readNextCommand(String cmdTips, PrintIndentLevel indentLevel, ReadTextMapping<T> readTextMapping) {
        while (true) {
            printText(cmdTips, indentLevel);
            final String cmdStr = SCANNER.nextLine();

            try {
                return readTextMapping.apply(cmdStr);
            } catch (ReadCommandMappingException e) {
                printText(String.format("- 信息输入错误: %s%n", e.getMessage()), indentLevel);
            }
        }
    }

    public static <T> T readNextCommand(String cmdTips, ReadTextMapping<T> readTextMapping) {
        return readNextCommand(cmdTips, PrintIndentLevel.ZERO, readTextMapping);
    }


    @FunctionalInterface
    public interface ReadTextMapping<T> {

        T apply(String s) throws ReadCommandMappingException;


        static ReadTextMapping<String> string() {
            return s -> s;
        }
    }
}
