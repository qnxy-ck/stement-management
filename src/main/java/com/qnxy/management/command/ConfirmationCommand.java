package com.qnxy.management.command;

import com.qnxy.management.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 退出确认命令定义
 *
 * @author Qnxy
 */
@Getter
@RequiredArgsConstructor
public enum ConfirmationCommand implements Command {

    NO(0, "取消"),
    YES(1, "确定");


    private final int confirmVal;
    private final String cmdDesc;

    @Override
    public int cmdVal() {
        return confirmVal;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }

    public static Optional<ConfirmationCommand> confirmValOf(Integer confirmVal) {

        return Arrays.stream(values())
                .filter(it -> it.confirmVal == confirmVal)
                .findFirst();

    }
}
