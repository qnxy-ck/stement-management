package com.qnxy.management.store;

import com.qnxy.management.data.entity.StudentInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

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
    private static final Map<StudentInfoKey, StudentInfo> STUDENT_INFO_MAP = new LinkedHashMap<>();
    

    public static Map<StudentInfoKey, StudentInfo> getStudentInfoMap() {
        return STUDENT_INFO_MAP;
    }

    public record StudentInfoKey(Integer id, String phone) {
    }

}
