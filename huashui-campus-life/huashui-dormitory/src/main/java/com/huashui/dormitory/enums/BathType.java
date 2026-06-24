package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BathType {

    PRIVATE("PRIVATE", "独立卫浴"),

    PUBLIC_STALL("PUBLIC_STALL", "楼层公共隔间"),

    DRY_WET_SEPARATED("DRY_WET_SEPARATED", "干湿分离独立卫浴");

    @EnumValue
    private final String code;

    private final String desc;

    BathType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}