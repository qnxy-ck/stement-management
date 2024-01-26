package com.qnxy.management.service.impl;

import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
import com.qnxy.management.data.entity.StudentInfo;
import com.qnxy.management.exceptions.StudentManagementException;
import com.qnxy.management.service.StudentInfoService;
import com.qnxy.management.store.MemoryDataStores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
                .setBirthday(LocalDate.now())
                .setGender(StudentInfo.Gender.UNKNOWN)
                .setPassword(DEFAULT_PASSWORD)
                .setCreateAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        final var flag = MemoryDataStores.getStudentInfoStore().add(studentInfo);
        if (!flag) {
            throw new StudentManagementException("该学生信息已存在, 无法添加 -> " + studentInfo.getPhone());
        }
        return studentInfo;
    }

    @Override
    public StudentInfo updateStudentById(StudentInfo studentInfo) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return MemoryDataStores.getStudentInfoStore()
                .removeIf(it -> it.getId().equals(id));
    }

    @Override
    public Optional<StudentInfo> findById(Integer id) {
        return MemoryDataStores.getStudentInfoStore()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<StudentInfo> findAll() {
        return new ArrayList<>(MemoryDataStores.getStudentInfoStore());
    }

    @Override
    public Page<StudentInfo> findAllByPage(PageReq pageReq) {
        List<StudentInfo> list = getPageData(pageReq, MemoryDataStores.getStudentInfoStore());

        return Page.of(list, MemoryDataStores.getStudentInfoStore().size(), pageReq);
    }

    @Override
    public Page<StudentInfo> findPageByAge(PageReq pageReq, Integer age) {
        return null;
    }

    @Override
    public Page<StudentInfo> findPageByPhone(PageReq pageReq, String phone) {
        return null;
    }


    private static List<StudentInfo> getPageData(PageReq pageReq, Set<StudentInfo> sourceCollection) {
        int startIndex = (pageReq.getCurrentPage() - 1) * pageReq.getPageSize();

        final List<StudentInfo> list = new ArrayList<>();

        Iterator<StudentInfo> iterator = sourceCollection.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            StudentInfo next = iterator.next();
            if (index >= startIndex) {

                list.add(next);

                if (list.size() == pageReq.getPageSize()) {
                    break;
                }
            }
            index++;
        }

        return list;
    }
}
