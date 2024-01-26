package com.qnxy.management;

import com.qnxy.management.command.ConfirmationCommand;
import com.qnxy.management.command.MoreFindMethodCommand;
import com.qnxy.management.command.PageCommand;
import com.qnxy.management.command.RootCommand;
import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.qnxy.management.util.PrintHelper.*;
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
                final RootCommand rootCommand = readNextCommand("请输入对应命令: ", ZERO, parseIntValEnum(RootCommand::cmdValOf));

                switch (rootCommand) {
                    case QUIT -> quit();
                    case ADD_STUDENT -> addStudent();
                    case UPDATE_STUDENT -> updateStudent();
                    case DELETE_STUDENT -> deleteStudent();
                    case FIND_ALL_STUDENT -> findAllStudent();
                    case FIND_PAGE_STUDENT -> findStudentByPage();
                    case MORE_FIND_METHOD -> {
                        //noinspection StatementWithEmptyBody
                        while (moreFindMethod()) ;
                    }
                }
            } catch (Exception e) {
                printText("操作失败: " + e.getMessage() + "\n", ZERO);
            }
        }
    }


    /**
     * 更多查询方式
     */
    private boolean moreFindMethod() {
        printCommandInfo(MoreFindMethodCommand.values(), ONE);

        final MoreFindMethodCommand moreFindMethodCommand = readNextCommand("请输入对应命令: ", ONE, parseIntValEnum(MoreFindMethodCommand::moreFindValOf));
        switch (moreFindMethodCommand) {
            case GO_BACK -> {
                return false;
            }
            case FIND_BY_ID -> this.findById();
            case FIND_PAGE_BY_AGE, FIND_PAGE_BY_PHONE -> printText("功能暂未实现, 尽请期待.\n", ONE);
        }

        return true;
    }

    private void findById() {
        final Integer index = readNextCommand("请输入需要查询的序号: ", ONE, parseToInt());
        final Optional<StudentInfo> studentInfoOptional = this.studentInfoService.findById(index);
        if (studentInfoOptional.isEmpty()) {
            printText("没有找到该序号对应的信息 -> " + index + "\n", ONE);
            return;
        }

        printStudent(studentInfoOptional.get(), ONE);
    }

    /**
     * 分页查找学生信息
     */
    private void findStudentByPage() {
        loopPageQuery(PageReq.defaultPage(), this.studentInfoService::findAllByPage);
    }

    private void loopPageQuery(PageReq pageReq, Function<PageReq, Page<StudentInfo>> pageFind) {
        PageReq p = pageReq;
        Page<StudentInfo> page = pageFind.apply(pageReq);
        printPageStudent(page, ONE);

        while (true) {
            printCommandInfo(PageCommand.values(), ONE);
            final PageCommand pageCommand = readNextCommand("请输入对应命令: ", ONE, parseIntValEnum(PageCommand::pageCmdValOf));

            switch (pageCommand) {
                case GO_BACK -> {
                    return;
                }

                case UP_PAGE -> {
                    if (p.getCurrentPage() <= 1) {
                        printText("已经是第一页了!\n", ONE);
                        continue;
                    }
                    p = p.upPage();
                }

                case DOWN_PAGE -> {
                    if (p.getCurrentPage() >= page.getTotalPage()) {
                        printText("已经是最后一页了!\n", ONE);
                        continue;
                    }
                    p = p.downPage();
                }

                case PAGE_NUM -> {
                    final Integer pn = readNextCommand("请输入指定页数: ", ONE, parseToInt());

                    p = PageReq.of(p.getPageSize(), pn);
                }
            }

            page = pageFind.apply(p);
            printPageStudent(page, ONE);
        }
    }


    /**
     * 查询所有学生信息
     */
    private void findAllStudent() {
        final List<StudentInfo> studentInfoList = this.studentInfoService.findAll();

        if (studentInfoList.isEmpty()) {
            printText("没有找到任何信息!\n", ONE);
            return;
        }

        studentInfoList
                .forEach(it -> printStudent(it, ONE));
    }

    /**
     * 删除学生信息
     */
    private void deleteStudent() {
        final Integer index = readNextCommand("请输入需要删除的序号: ", ZERO, parseToInt());

        printCommandInfo(ConfirmationCommand.values(), ONE);
        final ConfirmationCommand confirmationCommand = readNextCommand("确认删除吗: ", ONE, parseIntValEnum(ConfirmationCommand::confirmValOf));
        if (confirmationCommand != ConfirmationCommand.YES) {
            return;
        }

        boolean flag = this.studentInfoService.deleteById(index);
        printText(flag ? "删除成功\n" : "学生信息不存在 -> " + index + "\n", ONE);
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
        final Gender gender = readNextCommand(String.format("请输入性别, 可选项[%s]: ", Gender.genderNumList()), ONE, parseIntValEnum(Gender::genderValOf));

        StudentInfo studentInfo = new StudentInfo()
                .setNickname(nickName)
                .setActualName(actualName)
                .setPhone(phone)
                .setBirthday(birthday)
                .setGender(gender);
        ;
        studentInfo = this.studentInfoService.addStudentInfo(studentInfo);
        printText("添加成功: \n", ONE);
        printStudent(studentInfo, ONE);
    }

    /**
     * 退出系统
     */
    private void quit() {
        printCommandInfo(ConfirmationCommand.values(), ZERO);
        final ConfirmationCommand confirmationCommand = readNextCommand("是否确认退出: ", ZERO, parseIntValEnum(ConfirmationCommand::confirmValOf));

        if (confirmationCommand == ConfirmationCommand.YES) {
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
     * 解析用户输入的信息, 转换为对应枚举
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
                throw new ReadCommandMappingException("不存在的命令 -> " + it);
            }

            return mapping.apply(genderNum)
                    .orElseThrow(() -> new ReadCommandMappingException("不存在的命令 -> " + it));
        };
    }

    /**
     * 将字符串转换为int类型
     */
    private static ReadCommandUtil.ReadTextMapping<Integer> parseToInt() {
        return it -> {
            NOT_NULL_MAPPING_FUN.apply(it);
            try {
                return Integer.parseInt(it);
            } catch (NumberFormatException e) {
                throw new ReadCommandMappingException("请输入正确的数值 -> " + it);
            }
        };
    }

    /**
     * 打印学生信息
     */
    private static void printStudent(StudentInfo studentInfo, PrintIndentLevel indentLevel) {
        printText(
                String.format("- %s%n", studentInfo),
                indentLevel
        );
    }

    private static void printPageStudent(Page<StudentInfo> studentInfoPage, PrintIndentLevel indentLevel) {
        Collection<StudentInfo> studentInfos = studentInfoPage.getRecords();
        if (studentInfos.isEmpty()) {
            printText("没有找到任何信息!\n", ONE);
            return;
        }

        studentInfos.forEach(it -> printStudent(it, indentLevel));
        printPageInfo(studentInfoPage, indentLevel);
    }

}
