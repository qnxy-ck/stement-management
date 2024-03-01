package com.qnxy.management.util;

import com.qnxy.management.exceptions.ReadCommandMappingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * 读取数据不为空时进行转换和设置
     *
     * @param tips        提示信息
     * @param mapping     转换函数
     * @param setConsumer 设置
     * @param <T>         转换类型
     */
    public static <T> void readNotNullValueAndSet(String tips, ReadTextMapping<T> mapping, Consumer<T> setConsumer) {
        readNextCommand(String.format("请输入%s: ", tips), it -> {
            if (StringUtil.noBlank(it)) {
                T value = mapping.apply(it);
                setConsumer.accept(value);
            }
            return null;
        });
    }


    @FunctionalInterface
    public interface ReadTextMapping<T> {

        T apply(String s) throws ReadCommandMappingException;


        static ReadTextMapping<String> string() {
            return s -> s;
        }


        /**
         * 判断输入内容不能为空
         * 如果为空则抛出异常
         *
         * @return str
         */
        static ReadTextMapping<String> notNull() {
            return it -> {
                if (StringUtil.isBlank(it)) {
                    throw new ReadCommandMappingException("输入内容不能为空");
                }
                return it;
            };
        }


        /**
         * 将字符串转换为int类型
         *
         * @return 整数数值
         */
        static ReadTextMapping<Integer> parseToInt() {
            return it -> {
                notNull().apply(it);

                try {
                    return Integer.parseInt(it);
                } catch (NumberFormatException e) {
                    throw new ReadCommandMappingException("请输入正确的数值 -> " + it);
                }
            };
        }

        /**
         * 解析用户输入的信息, 转换为对应枚举
         *
         * @param mapping 转换到那个美剧
         * @param <E>     枚举类型
         * @return .
         */
        static <E extends Enum<E>> ReadTextMapping<E> parseIntValEnum(Function<Integer, Optional<E>> mapping) {
            return it -> {
                final Integer intVal = parseToInt().apply(it);

                return mapping.apply(intVal)
                        .orElseThrow(() -> new ReadCommandMappingException("不存在的命令 -> " + it));
            };
        }

        static Optional<String> s() {
            return Optional.empty();
        }

        /**
         * 将字符串转日期类型
         */
        static ReadTextMapping<LocalDate> toLocalDate() {
            return it -> {
                notNull().apply(it);

                try {
                    return DateUtil.textToLocalDate(it);
                } catch (Exception e) {
                    throw new ReadCommandMappingException("输入信息错误, 不是一个正确的日期格式 -> " + it);
                }
            };
        }

    }
}
