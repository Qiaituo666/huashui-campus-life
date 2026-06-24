package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BalconyType {

    STANDARD("STANDARD", "标准阳台"),

    LARGE("LARGE", "超大阳台");

    @EnumValue
    private final String code;

    private final String desc;

    BalconyType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}