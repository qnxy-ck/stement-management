package com.qnxy.management;

import static com.qnxy.management.util.PrintHelper.printBanner;

/**
 * 学生信息管理入口
 *
 * @author Qnxy
 */
public class StudentManagementSystemApplication {


    public static void main(String[] args) {
        // 打印banner信息
        printBanner();

        // 启动服务
        CommandService.runSystem();
    }


}
