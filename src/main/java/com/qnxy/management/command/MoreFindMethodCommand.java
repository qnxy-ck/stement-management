package com.qnxy.management.command;

import com.qnxy.management.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 更多查询相关命令信息定义
 * 
 * @author Qnxy
 */
@RequiredArgsConstructor
@Getter
public enum MoreFindMethodCommand implements Command {
    GO_BACK(0, "返回上一级"),

    FIND_PAGE_BY_AGE(1, "查询指定年龄学生信息"),
    FIND_PAGE_BY_PHONE(2, "查询指定手机号学生信息"),
    ;

    private final int cmdNum;
    private final String cmdDesc;

    @Override
    public int cmdNum() {
        return cmdNum;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }
}
