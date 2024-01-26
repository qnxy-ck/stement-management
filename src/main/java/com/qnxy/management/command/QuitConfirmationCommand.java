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
public enum QuitConfirmationCommand implements Command {

    NO(0, "暂不退出"),
    YES(1, "=== 确认退出 ===");


    private final int quitNum;
    private final String cmdDesc;

    @Override
    public int cmdNum() {
        return quitNum;
    }

    @Override
    public String cmdDesc() {
        return cmdDesc;
    }

    public static Optional<QuitConfirmationCommand> quitNumOf(Integer quitNum) {

        return Arrays.stream(values())
                .filter(it -> it.quitNum == quitNum)
                .findFirst();

    }
}
