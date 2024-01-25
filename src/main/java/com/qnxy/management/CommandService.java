package com.qnxy.management;

import com.qnxy.management.command.QuitConfirmationCommand;
import com.qnxy.management.command.RootCommand;
import com.qnxy.management.exceptions.StudentManagementException;
import com.qnxy.management.util.PrintIndentLevel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;
import java.util.function.Function;

import static com.qnxy.management.util.PrintHelper.printCommandInfo;
import static com.qnxy.management.util.PrintHelper.printText;
import static com.qnxy.management.util.PrintIndentLevel.ZERO;

/**
 * 学生管理命令服务
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandService {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void runSystem() {

        //noinspection InfiniteLoopStatement
        while (true) {
            printCommandInfo(RootCommand.values(), ZERO);

            try {
                final RootCommand rootCommand = readNextCommand("请输入对应命令: ", ZERO, RootCommand::cmdNumOf)
                        .orElseThrow(() -> new StudentManagementException("输入指令不存在"));

                switch (rootCommand) {
                    case QUIT -> quit();
                    case ADD_STUDENT -> addStudent();
                    case UPDATE_STUDENT -> updateStudent();
                    case DELETE_STUDENT -> deleteStudent();
                    case FIND_ALL_STUDENT -> findAllStudent();
                    case FIND_PAGE_STUDENT -> findPageStudent();
                    case MORE_FIND_METHOD -> moreFindMethod();
                }
            } catch (Exception e) {
                printText("操作失败: " + e.getMessage(), ZERO);
            }
        }
    }

    /**
     * 等待并获取用户输入
     * 将输入结果进行转换后返回
     *
     * @param cmdTips     等待用户输入提示信息
     * @param indentLevel 提示信息打印层级
     * @param mapping     转换函数
     * @param <T>         .
     * @return .
     */
    private static <T> T readNextCommand(String cmdTips, PrintIndentLevel indentLevel, Function<String, T> mapping) {
        printText(cmdTips, indentLevel);
        final String cmdStr = SCANNER.nextLine();
        try {
            return mapping.apply(cmdStr);
        } catch (Exception e) {
            throw new StudentManagementException("信息输入错误 -> " + e.getMessage());
        }
    }

    /**
     * 更多查询方式
     */
    private static void moreFindMethod() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 分页查找学生信息
     */
    private static void findPageStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 查询所有学生信息
     */
    private static void findAllStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 删除学生信息
     */
    private static void deleteStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 更新学生信息
     */
    private static void updateStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 添加学生信息
     */
    private static void addStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 退出系统
     */
    private static void quit() {
        printCommandInfo(QuitConfirmationCommand.values(), ZERO);
        final QuitConfirmationCommand quitConfirmationCommand = readNextCommand("是否确认退出: ", ZERO, QuitConfirmationCommand::quitNumOf)
                .orElseThrow(() -> new StudentManagementException("输入错误, 退出命令不存在"));

        if (quitConfirmationCommand == QuitConfirmationCommand.YES) {
            printText("Bye!", ZERO);
            System.exit(0);
        }

    }

}
