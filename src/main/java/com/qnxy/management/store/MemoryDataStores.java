package com.qnxy.management.store;

import com.qnxy.management.data.entity.StudentInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 内存存储信息
 *
 * @author Qnxy
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemoryDataStores {

    /**
     * 学生信息
     */
    private static final Set<StudentInfo> STUDENT_INFO_SET = new LinkedHashSet<>();


    public static Set<StudentInfo> getStudentInfoStore() {
        return STUDENT_INFO_SET;
    }


}
