package com.qnxy.management.data.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.qnxy.management.util.DateUtil.localDateToText;

/**
 * 学生信息实体类
 *
 * @author Qnxy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class StudentInfo {

    /**
     * 自增序号
     * 唯一
     * 必填
     */
    private Integer id;

    /**
     * 昵称
     * 必填
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String actualName;

    /**
     * 学生手机号
     * 唯一
     * 不可修改
     * 必填
     */
    private String phone;

    /**
     * 密码
     * 默认: 123456
     */
    private String password;

    /**
     * 学生生日
     * 必填
     */
    private LocalDate birthday;

    /**
     * 性别
     * {@link Gender}
     */
    private Gender gender;

    /**
     * 记录创建时间
     * 必填
     */
    private LocalDateTime createAt;

    /**
     * 记录修改时间
     * 必填
     */
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfo that = (StudentInfo) o;

        return Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return phone != null ? phone.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "学生信息{" +
                "id=" + id +
                ", 昵称='" + nickname + '\'' +
                ", 真实姓名='" + actualName + '\'' +
                ", 手机号='" + phone + '\'' +
                ", 密码='" + password + '\'' +
                ", 生日=" + localDateToText(birthday) +
                ", 性别=" + gender.genderName +
                ", 创建时间=" + localDateToText(createAt) +
                ", 更新时间=" + localDateToText(updatedAt) +
                '}';
    }

    /**
     * 性别信息
     */
    @RequiredArgsConstructor
    @Getter
    public enum Gender {
        /**
         * 未知/不公开
         */
        UNKNOWN(0, "不公开"),

        /**
         * 女孩
         */
        GIRL(1, "女"),

        /**
         * 男孩
         */
        BOY(2, "男");

        private final int genderVal;
        private final String genderName;

        private static final String GENDER_NUMBER_LIST_STR = Arrays.stream(values())
                .map(it -> String.format("%s: %s", it.genderName, it.genderVal))
                .collect(Collectors.joining(", "));

        public static String genderNumList() {
            return GENDER_NUMBER_LIST_STR;
        }

        public static Optional<Gender> genderValOf(Integer genderVal) {
            return Arrays.stream(values())
                    .filter(it -> it.genderVal == genderVal)
                    .findFirst();

        }
    }

}
