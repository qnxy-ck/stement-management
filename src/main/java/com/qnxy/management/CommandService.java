package com.qnxy.management;

import com.qnxy.management.command.QuitConfirmationCommand;
import com.qnxy.management.command.RootCommand;
import com.qnxy.management.data.entity.StudentInfo;
import com.qnxy.management.data.entity.StudentInfo.Gender;
import com.qnxy.management.exceptions.ReadCommandMappingException;
import com.qnxy.management.service.StudentInfoService;
import com.qnxy.management.util.DateUtil;
import com.qnxy.management.util.PrintIndentLevel;
import com.qnxy.management.util.ReadCommandUtil;
import com.qnxy.management.util.StringUtil;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import static com.qnxy.management.util.PrintHelper.printCommandInfo;
import static com.qnxy.management.util.PrintHelper.printText;
import static com.qnxy.management.util.PrintIndentLevel.ONE;
import static com.qnxy.management.util.PrintIndentLevel.ZERO;
import static com.qnxy.management.util.ReadCommandUtil.readNextCommand;

/**
 * 学生管理命令服务
 *
 * @author Qnxy
 */
@RequiredArgsConstructor
public class CommandService {

    private static final ReadCommandUtil.ReadTextMapping<String> NOT_NULL_MAPPING_FUN = it -> {
        if (StringUtil.isBlank(it)) {
            throw new ReadCommandMappingException("输入内容不能为空");
        }
        return it;
    };


    private final StudentInfoService studentInfoService;


    public void runSystem() {

        //noinspection InfiniteLoopStatement
        while (true) {
            printCommandInfo(RootCommand.values(), ZERO);

            try {
                final RootCommand rootCommand = readNextCommand("请输入对应命令: ", ZERO, parseIntValEnum(RootCommand::cmdNumOf));

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
                printText("操作失败: " + e.getMessage() + "\n", ZERO);
            }
        }
    }


    /**
     * 更多查询方式
     */
    private void moreFindMethod() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 分页查找学生信息
     */
    private void findPageStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 查询所有学生信息
     */
    private void findAllStudent() {
        studentInfoService.findAll()
                .forEach(it -> printStudent(it, ONE));
    }

    /**
     * 删除学生信息
     */
    private void deleteStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 更新学生信息
     */
    private void updateStudent() {
        printText("功能暂未实现, 尽请期待.\n", ZERO);
    }

    /**
     * 添加学生信息
     */
    private void addStudent() {
        final String nickName = readNextCommand("请输入昵称: ", ONE, NOT_NULL_MAPPING_FUN);
        final String actualName = readNextCommand("请输入真实姓名: ", ONE, NOT_NULL_MAPPING_FUN);
        final String phone = readNextCommand("请输入手机号: ", ONE, NOT_NULL_MAPPING_FUN);
        final LocalDate birthday = readNextCommand("请输入生日, 格式为[yyyy-MM-dd]: ", ONE, toLocalDate());
        final Gender gender = readNextCommand(String.format("请输入性别, 可选项[%s]: ", Gender.genderNumList()), ONE, parseIntValEnum(Gender::genderNumOf));

        StudentInfo studentInfo = new StudentInfo()
                .setNickname(nickName)
                .setActualName(actualName)
                .setPhone(phone)
                .setBirthday(birthday)
                .setGender(gender);

        studentInfo = studentInfoService.addStudentInfo(studentInfo);
        printText("添加成功: \n", ONE);
        printStudent(studentInfo, ONE);
    }

    /**
     * 退出系统
     */
    private void quit() {
        printCommandInfo(QuitConfirmationCommand.values(), ZERO);
        final QuitConfirmationCommand quitConfirmationCommand = readNextCommand("是否确认退出: ", ZERO, parseIntValEnum(QuitConfirmationCommand::quitNumOf));

        if (quitConfirmationCommand == QuitConfirmationCommand.YES) {
            printText("Bye!", ZERO);
            System.exit(0);
        }

    }

    /**
     * 将字符串转日期类型
     */
    private static ReadCommandUtil.ReadTextMapping<LocalDate> toLocalDate() {
        return text -> {
            NOT_NULL_MAPPING_FUN.apply(text);

            try {
                return DateUtil.textToLocalDate(text);
            } catch (Exception e) {
                throw new ReadCommandMappingException("输入信息错误, 不是一个正确的日期格式 -> " + text);
            }
        };
    }

    /**
     * 解析用户输入的信息, 转换为对应美剧
     *
     * @param mapping 转换到那个美剧
     * @param <E>     枚举类型
     * @return .
     */
    private static <E extends Enum<E>> ReadCommandUtil.ReadTextMapping<E> parseIntValEnum(Function<Integer, Optional<E>> mapping) {
        return it -> {
            NOT_NULL_MAPPING_FUN.apply(it);
            final int genderNum;
            try {
                genderNum = Integer.parseInt(it);
            } catch (NumberFormatException e) {
                throw new ReadCommandMappingException("不存在的类型 -> " + it);
            }

            return mapping.apply(genderNum)
                    .orElseThrow(() -> new ReadCommandMappingException("不存在的类型 -> " + it));
        };
    }


    /**
     * 打印学生信息
     */
    public static void printStudent(StudentInfo studentInfo, PrintIndentLevel indentLevel) {
        printText(
                String.format("- %s", studentInfo),
                indentLevel
        );
    }

}
