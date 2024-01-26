package com.qnxy.management.command;

import com.qnxy.management.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 分页命令数据定义
 *
 * @author Qnxy
 */
@RequiredArgsConstructor
@Getter
public enum PageCommand implements Command {
    GO_BACK(0, "返回上一级"),
    UP_PAGE(1, "上一页"),
    DOWN_PAGE(2, "下一页"),
    PAGE_NUM(3, "指定页数查询");

    private final int pageCmdVal;
    private final String cmdDesc;

    @Override
    public int cmdVal() {
        return pageCmdVal;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }

    public static Optional<PageCommand> pageCmdValOf(Integer pageCmdVal) {
        return Arrays.stream(values())
                .filter(it -> it.pageCmdVal == pageCmdVal)
                .findFirst();
    }
}
