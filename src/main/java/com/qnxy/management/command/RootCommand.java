package com.qnxy.management.command;

import com.qnxy.management.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 顶级命令信息定义
 *
 * @author Qnxy
 */
@RequiredArgsConstructor
@Getter
public enum RootCommand implements Command {

    QUIT(0, "退出系统"),
    ADD_STUDENT(1, "添加学生信息"),
    UPDATE_STUDENT(2, "修改学生信息"),
    DELETE_STUDENT(3, "删除学生信息"),
    FIND_ALL_STUDENT(4, "查看所有学生信息"),
    FIND_PAGE_STUDENT(5, "分页查看所有学生信息"),
    MORE_FIND_METHOD(6, "更多查询方式"),

    ;

    private final int cmdVal;
    private final String cmdDesc;


    @Override
    public int cmdVal() {
        return cmdVal;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }

    public static Optional<RootCommand> cmdValOf(Integer cmdVal) {
        return Arrays.stream(values())
                .filter(it -> it.cmdVal == cmdVal)
                .findFirst();

    }
}
