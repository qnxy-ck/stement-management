package com.qnxy.management.store;

import com.qnxy.management.data.entity.StudentInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    static {

        for (int i = 1; i <= 101; i++) {
            STUDENT_INFO_SET.add(
                    new StudentInfo()
                            .setId(i)
                            .setNickname("qnxy")
                            .setActualName("ck")
                            .setPhone("123465787654" + i)
                            .setPassword("123456")
                            .setBirthday(LocalDate.now())
                            .setGender(StudentInfo.Gender.UNKNOWN)
                            .setCreateAt(LocalDateTime.now())
                            .setUpdatedAt(LocalDateTime.now())
            );
        }
    }

    public static Set<StudentInfo> getStudentInfoStore() {
        return STUDENT_INFO_SET;
    }


}
