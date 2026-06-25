package com.huashui.user.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 系统用户类型枚举。
 * <p>
 * 区分四类用户角色，存储在 sys_user.user_type 字段（枚举名）。
 * MyBatis Plus 自动完成枚举与数据库字符串的互转。
 * </p>
 */
@Getter
@Schema(description = "用户类型")
public enum UserType {

    @Schema(description = "学生")
    STUDENT("学生"),

    @Schema(description = "保洁")
    CLEANER("保洁"),

    @Schema(description = "宿管")
    DORM_MANAGER("宿管"),

    @Schema(description = "超级管理员")
    SUPER_ADMIN("超级管理员");

    private final String desc;

    UserType(String desc) {
        this.desc = desc;
    }
}
