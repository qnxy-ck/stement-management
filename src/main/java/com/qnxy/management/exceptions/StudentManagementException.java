package com.qnxy.management.exceptions;

import java.util.function.Supplier;

/**
 * 学生信息管理相关异常
 *
 * @author Qnxy
 */
public class StudentManagementException extends RuntimeException {

    public StudentManagementException(String message) {
        super(message);
    }


    public static Supplier<StudentManagementException> errorMsgOf(String message) {
        return () -> new StudentManagementException(message);
    }
    
}
