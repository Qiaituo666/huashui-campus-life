package com.huashui.auth.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "权限类型")
public enum PermissionType {

    MENU("菜单"),

    BUTTON("按钮"),

    API("接口");

    private final String desc;

    PermissionType(String desc) {
        this.desc = desc;
    }
}