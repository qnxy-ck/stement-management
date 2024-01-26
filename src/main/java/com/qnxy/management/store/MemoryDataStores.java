package com.qnxy.management.store;

import com.qnxy.management.data.Page;
import com.qnxy.management.data.PageReq;
import com.qnxy.management.data.entity.StudentInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static <T> Optional<T> findOneByCondition(Set<T> sourceCollection, Predicate<T> predicate) {
        return sourceCollection.stream()
                .filter(predicate)
                .findFirst();
    }

    public static <T> List<T> findAllByCondition(Set<T> sourceCollection, Predicate<T> predicate) {
        return sourceCollection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> Page<T> findPageByCondition(PageReq pageReq, Set<T> sourceCollection, Predicate<T> predicate) {
        int startIndex = (pageReq.getCurrentPage() - 1) * pageReq.getPageSize();

        final List<T> list = new ArrayList<>();

        Iterator<T> iterator = sourceCollection.iterator();
        int index = 0;
        int findCount = 0;
        while (iterator.hasNext()) {
            final T next = iterator.next();

            if (!predicate.test(next)) {
                continue;
            }

            findCount += 1;

            if (index >= startIndex && list.size() != pageReq.getPageSize()) {
                list.add(next);
            }

            index++;
        }
        return Page.of(list, findCount, pageReq);

    }
}
