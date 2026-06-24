package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum FloorType {

    TILE("TILE", "瓷砖"),

    CEMENT("CEMENT", "水泥");

    @EnumValue
    private final String code;

    private final String desc;

    FloorType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}