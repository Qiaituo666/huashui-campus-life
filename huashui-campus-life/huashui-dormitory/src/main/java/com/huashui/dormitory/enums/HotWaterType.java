package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum HotWaterType {

    ALL_DAY("ALL_DAY", "24小时供应"),

    LIMITED("LIMITED", "限时供应");

    @EnumValue
    private final String code;

    private final String desc;

    HotWaterType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}