package com.qnxy.management.service;

import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
import com.qnxy.management.data.entity.StudentInfo;

import java.util.List;
import java.util.Optional;

/**
 * 学生信息管理Service
 *
 * @author Qnxy
 */
public interface StudentInfoService {

    /**
     * 添加一条记录
     *
     * @param studentInfo 学生信息
     * @return 添加成功返回添加后的数据
     */
    StudentInfo addStudentInfo(StudentInfo studentInfo);

    /**
     * 根据ID修改学生信息
     *
     * @param studentInfo 待修改数据
     *                    ID必填
     *                    为null的参数不进行修改
     * @return 修改后的数据
     */
    StudentInfo updateStudentById(StudentInfo studentInfo);

    /**
     * 根据ID删除一条学生信息
     *
     * @param id 删除学生信息的ID
     * @return 是否删除成功 成功: true
     */
    boolean deleteById(Integer id);

    /**
     * 根据ID获取一个
     *
     * @param id ID
     * @return .
     */
    Optional<StudentInfo> findById(Integer id);

    /**
     * 查询所有学生信息
     *
     * @return .
     */
    List<StudentInfo> findAll();

    /**
     * 分页查询学生信息
     *
     * @param pageReq 分页参数
     * @return .
     */
    Page<StudentInfo> findAllByPage(PageReq pageReq);

    /**
     * 查询指定年龄的学生信息
     * 分页查询数据
     *
     * @param pageReq 分页参数
     * @param age     查询的学生年龄
     * @return .
     */
    Page<StudentInfo> findPageByAge(PageReq pageReq, Integer age);

    /**
     * 查询指定手机号的学生信息
     * 分页查询数据
     *
     * @param phone 查询的学生年龄
     * @return .
     */
    Optional<StudentInfo> findPageByPhone(String phone);

}
