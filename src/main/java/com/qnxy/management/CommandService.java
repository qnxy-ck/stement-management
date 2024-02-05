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
import com.qnxy.management.util.PrintIndentLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.qnxy.management.util.PrintHelper.*;
import static com.qnxy.management.util.PrintIndentLevel.*;
import static com.qnxy.management.util.ReadCommandUtil.ReadTextMapping.*;
import static com.qnxy.management.util.ReadCommandUtil.readNextCommand;
import static com.qnxy.management.util.ReadCommandUtil.readNotNullValueAndSet;

/**
 * 学生管理命令服务
 *
 * @author Qnxy
 */
@RequiredArgsConstructor
public class CommandService {

    private final StudentInfoService studentInfoService;


    public void runSystem() {

        //noinspection InfiniteLoopStatement
        while (true) {
            printCommandList(RootCommand.values(), ZERO);

            try {
                final RootCommand rootCommand = readNextCommand("请输入对应命令: ", parseIntValEnum(RootCommand::cmdValOf));

                switch (rootCommand) {
                    case QUIT -> quit();
                    case ADD_STUDENT -> addStudent();
                    case UPDATE_STUDENT -> updateStudent();
                    case DELETE_STUDENT -> deleteStudent();
                    case FIND_ALL_STUDENT -> findAllStudent();
                    case FIND_PAGE_STUDENT -> findStudentByPage();
                    case MORE_FIND_METHOD -> moreFindMethod();
                }
            } catch (Exception e) {
                printText("操作失败: " + e.getMessage() + "\n");
            }
        }
    }


    /**
     * 更多查询方式
     */
    private void moreFindMethod() {
        while (true) {
            printCommandList(MoreFindMethodCommand.values(), ONE);

            final MoreFindMethodCommand moreFindMethodCommand = readNextCommand("请输入对应命令: ", ONE, parseIntValEnum(MoreFindMethodCommand::moreFindValOf));
            switch (moreFindMethodCommand) {
                case GO_BACK -> {
                    return;
                }
                case FIND_BY_ID -> this.findById();
                case FIND_PAGE_BY_AGE -> this.findPageByAge();
                case FIND_BY_PHONE -> this.findByPhone();
                case FIND_PAGE_BY_ACTUAL_NAME -> this.findPageByActualName();
            }
        }
    }

    /**
     * 根据年龄粉盒查询
     */
    private void findPageByAge() {
        final int age = readNextCommand("请输入年龄: ", ONE, parseToInt());

        loopPageQuery(TWO, it -> this.studentInfoService.findPageByAge(it, age));
    }

    /**
     * 根据实际名称分页查询
     */
    private void findPageByActualName() {
        final String actualName = readNextCommand("请输入姓名: ", ONE, notNull());

        loopPageQuery(TWO, it -> this.studentInfoService.findAllByActualName(it, actualName));
    }

    /**
     * 根据手机号分页查询
     */
    private void findByPhone() {
        final String phone = readNextCommand("请输入查询手机号: ", ONE, notNull());

        this.studentInfoService.findAllByPhone(phone)
                .ifPresentOrElse(
                        it -> printStudent(it, ONE),
                        () -> printText("没有找到任何匹配数据!\n", TWO)
                );

    }

    /**
     * 指定序号查询一条
     */
    private void findById() {
        final Integer index = readNextCommand("请输入需要查询的序号: ", ONE, parseToInt());

        this.studentInfoService.findById(index)
                .ifPresentOrElse(
                        it -> printStudent(it, ONE),
                        () -> printText("没有找到任何匹配数据!\n", TWO)
                );
    }

    /**
     * 分页查找学生信息
     */
    private void findStudentByPage() {
        loopPageQuery(ONE, this.studentInfoService::findAllByPage);
    }

    private void loopPageQuery(PrintIndentLevel indentLevel, Function<PageReq, Page<StudentInfo>> pageFind) {
        // 第一次使用默认分页信息
        PageReq p = PageReq.defaultPage();

        // 使用默认分页信息第一次查询
        Page<StudentInfo> page = pageFind.apply(p);

        // 如果是第一次查询并且查询结果为空则直接返回
        if (page.getRecords().isEmpty()) {
            printText("没有找到任何信息!\n", indentLevel);
            return;
        }

        printPageStudent(page, indentLevel);


        while (true) {
            // 打印出第一次查询的结果后, 打印分页操作相关命令信息
            // 并等待用户输入
            printCommandList(PageCommand.values(), indentLevel);
            final PageCommand pageCommand = readNextCommand("请输入对应命令: ", indentLevel, parseIntValEnum(PageCommand::pageCmdValOf));

            // 根据输入做出对应指令
            switch (pageCommand) {
                case GO_BACK -> {
                    return;
                }

                case UP_PAGE -> {
                    if (p.getCurrentPage() <= 1) {
                        printText("已经是第一页了!\n", indentLevel);
                        continue;
                    }
                    p = p.upPage();
                }

                case DOWN_PAGE -> {
                    if (p.getCurrentPage() >= page.getTotalPage()) {
                        printText("已经是最后一页了!\n", indentLevel);
                        continue;
                    }
                    p = p.downPage();
                }

                case PAGE_NUM -> {
                    final int pn = readNextCommand("请输入指定页数: ", indentLevel, parseToInt());
                    if (pn > page.getTotalPage() && pn <= 0) {
                        printText("输入页数范围不正确!\n");
                        continue;
                    }

                    p = PageReq.of(p.getPageSize(), pn);
                }

                case SET_DEFAULT_PAGE_SIZE -> {
                    final int defaultPageSize = readNextCommand("请输入分页默认大小(仅本次查询生效): ", indentLevel, it -> {
                        Integer i = parseToInt().apply(it);

                        if (i < PageReq.DEFAULT_PAGE_SIZE) {
                            throw new ReadCommandMappingException("每页最小数量为: " + PageReq.DEFAULT_PAGE_SIZE);
                        }

                        if (i > PageReq.DEFAULT_MAX_PAGE_SIZE) {
                            throw new ReadCommandMappingException("每页最大数量为: " + PageReq.DEFAULT_MAX_PAGE_SIZE);
                        }

                        return i;
                    });

                    p = PageReq.of(defaultPageSize, 1);
                }

            }

            // 根据用户的操作, 再次查询用户信息
            page = pageFind.apply(p);
            printPageStudent(page, indentLevel);

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
        final Integer index = readNextCommand("请输入需要删除的序号: ", parseToInt());

        printCommandList(ConfirmationCommand.values(), ONE);
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
        final Integer index = readNextCommand("请输入需要删除的序号: ", parseToInt());
        final Optional<StudentInfo> studentInfoOptional = this.studentInfoService.findById(index);


        final Consumer<StudentInfo> updateAction = it -> {
            printText("原始信息内容:\n");
            printStudent(it, ONE);

            printText("提示: 如果无需更新直接回车\n");

            readNotNullValueAndSet("昵称", string(), it::setNickname);
            readNotNullValueAndSet("密码", string(), it::setPassword);
            readNotNullValueAndSet("性别", parseIntValEnum(Gender::genderValOf), it::setGender);
            readNotNullValueAndSet("生日", toLocalDate(), it::setBirthday);

            StudentInfo studentInfo = this.studentInfoService.updateStudentById(it);
            printText("更新完成\n");
            printStudent(studentInfo, ONE);

        };

        studentInfoOptional.ifPresentOrElse(updateAction, () -> printText("该序号不存在, 无法更新 -> " + index + "\n"));
    }

    /**
     * 添加学生信息
     */
    private void addStudent() {
        final String phone = readNextCommand("请输入手机号: ", ONE, notNull());
        final String nickName = readNextCommand("请输入昵称: ", ONE, notNull());
        final String actualName = readNextCommand("请输入真实姓名: ", ONE, notNull());
        final LocalDate birthday = readNextCommand("请输入生日, 格式为[yyyy-MM-dd]: ", ONE, toLocalDate());
        final Gender gender = readNextCommand(String.format("请输入性别, 可选项[%s]: ", Gender.genderNumList()), ONE, parseIntValEnum(Gender::genderValOf));

        StudentInfo studentInfo = new StudentInfo()
                .setNickname(nickName)
                .setActualName(actualName)
                .setPhone(phone)
                .setBirthday(birthday)
                .setGender(gender);

        studentInfo = this.studentInfoService.addStudentInfo(studentInfo);
        printText("添加成功: \n", ONE);
        printStudent(studentInfo, ONE);
    }

    /**
     * 退出系统
     */
    private void quit() {
        printCommandList(ConfirmationCommand.values(), ZERO);
        final ConfirmationCommand confirmationCommand = readNextCommand("是否确认退出: ", parseIntValEnum(ConfirmationCommand::confirmValOf));

        if (confirmationCommand == ConfirmationCommand.YES) {
            printText("Bye!");
            System.exit(0);
        }
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
        // 打印学生信息
        studentInfoPage.getRecords()
                .forEach(it -> printStudent(it, indentLevel));

        // 打印页数信息
        printPageInfo(studentInfoPage, indentLevel);
    }

}
