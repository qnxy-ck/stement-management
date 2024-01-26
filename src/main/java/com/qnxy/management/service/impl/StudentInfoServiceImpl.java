package com.qnxy.management.service.impl;

import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
import com.qnxy.management.data.entity.StudentInfo;
import com.qnxy.management.service.StudentInfoService;
import com.qnxy.management.store.MemoryDataStores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生信息管理Service impl
 *
 * @author Qnxy
 */
public class StudentInfoServiceImpl implements StudentInfoService {

    private static final String DEFAULT_PASSWORD = "123456";

    private static int INDEX = 1;

    private static int nextIndex() {
        return INDEX++;
    }

    @Override
    public StudentInfo addStudentInfo(StudentInfo studentInfo) {
        studentInfo.setId(nextIndex())
                .setPassword(DEFAULT_PASSWORD)
                .setCreateAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        MemoryDataStores.getStudentInfoMap()
                .put(new MemoryDataStores.StudentInfoKey(studentInfo.getId(), studentInfo.getPhone()), studentInfo);

        return studentInfo;
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
        return new ArrayList<>(MemoryDataStores.getStudentInfoMap().values());
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
