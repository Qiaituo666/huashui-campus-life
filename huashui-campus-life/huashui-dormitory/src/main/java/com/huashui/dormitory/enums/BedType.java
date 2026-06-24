package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BedType {

    BUNK_DESK("BUNK_DESK", "上床下桌"),

    BUNK_MIXED("BUNK_MIXED", "上下铺混合");

    @EnumValue
    private final String code;

    private final String desc;

    BedType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}