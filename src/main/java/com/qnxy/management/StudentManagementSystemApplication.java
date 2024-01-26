package com.qnxy.management;

import com.qnxy.management.service.impl.StudentInfoServiceImpl;

import static com.qnxy.management.util.PrintHelper.printBanner;

/**
 * 学生信息管理入口
 *
 * @author Qnxy
 */
public class StudentManagementSystemApplication {

    private static final CommandService COMMAND_SERVICE = new CommandService(
            new StudentInfoServiceImpl()
    ); 

    public static void main(String[] args) {
        // 打印banner信息
        printBanner();

        // 启动服务
        COMMAND_SERVICE.runSystem();
    }


}
