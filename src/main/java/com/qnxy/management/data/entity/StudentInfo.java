package com.qnxy.management.data.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    /**
     * 性别信息
     */
    @RequiredArgsConstructor
    @Getter
    public enum Gender {
        /**
         * 未知/不公开
         */
        UNKNOWN(0),

        /**
         * 女孩
         */
        GIRL(1),

        /**
         * 男孩
         */
        BOY(2);

        private final int genderNum;

    }

}
