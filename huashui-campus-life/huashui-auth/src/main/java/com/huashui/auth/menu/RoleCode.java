package com.huashui.auth.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "角色编码")
public enum RoleCode {

    STUDENT("学生"),

    CLEANER("保洁"),

    DORM_MANAGER("宿管"),

    SUPER_ADMIN("超级管理员");

    private final String desc;

    RoleCode(String desc) {
        this.desc = desc;
    }
}