package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum RoomType {

    FOUR("FOUR", "四人间"),

    SIX("SIX", "六人间");

    @EnumValue
    private final String code;

    private final String desc;

    RoomType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}