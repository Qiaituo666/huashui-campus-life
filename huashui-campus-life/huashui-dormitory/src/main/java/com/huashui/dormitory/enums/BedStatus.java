package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BedStatus {

    FREE(0, "空闲"),

    OCCUPIED(1, "已入住"),

    RESERVED(2, "预留");

    @EnumValue
    private final Integer code;

    private final String desc;

    BedStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}