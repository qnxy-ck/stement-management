package com.qnxy.management.service.impl;

import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
import com.qnxy.management.data.entity.StudentInfo;
import com.qnxy.management.service.StudentInfoService;

import java.util.List;

/**
 * 学生信息管理Service impl
 *
 * @author Qnxy
 */
public class StudentInfoServiceImpl implements StudentInfoService {
    
    @Override
    public StudentInfo addStudentInfo(StudentInfo studentInfo) {
        return null;
    }

    @Override
    public StudentInfo updateStudentById(StudentInfo studentInfo) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public List<StudentInfo> findAll() {
        return null;
    }

    @Override
    public Page<StudentInfo> findAllByPage(PageReq pageReq) {
        return null;
    }

    @Override
    public Page<StudentInfo> findPageByAge(PageReq pageReq, Integer age) {
        return null;
    }

    @Override
    public Page<StudentInfo> findPageByPhone(PageReq pageReq, String phone) {
        return null;
    }
}
