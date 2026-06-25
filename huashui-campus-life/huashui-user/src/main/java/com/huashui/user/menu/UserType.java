package com.huashui.user.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

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