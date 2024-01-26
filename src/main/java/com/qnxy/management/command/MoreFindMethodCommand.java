package com.qnxy.management.command;

import com.qnxy.management.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 更多查询相关命令信息定义
 *
 * @author Qnxy
 */
@RequiredArgsConstructor
@Getter
public enum MoreFindMethodCommand implements Command {
    GO_BACK(0, "返回上一级"),

    FIND_BY_ID(1, "指定序号查询学生信息"),
    FIND_PAGE_BY_AGE(2, "查询指定年龄学生信息"),
    FIND_PAGE_BY_PHONE(3, "查询指定手机号学生信息"),
    ;

    private final int moreFindVal;
    private final String cmdDesc;

    @Override
    public int cmdVal() {
        return moreFindVal;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }

    public static Optional<MoreFindMethodCommand> moreFindValOf(Integer moreFindVal) {
        return Arrays.stream(values())
                .filter(it -> it.moreFindVal == moreFindVal)
                .findFirst();
    }
}
