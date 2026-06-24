package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RepairType {

    PIPE("PIPE", "水管"),
    LIGHT("LIGHT", "灯具"),
    LOCK("LOCK", "门锁"),
    AC("AC", "空调"),
    NETWORK("NETWORK", "网络"),
    DOOR_WINDOW("DOOR_WINDOW", "门窗"),
    FURNITURE("FURNITURE", "家具"),
    HEATING("HEATING", "暖气"),
    OTHER("OTHER", "其他");

    @EnumValue
    private final String code;

    private final String desc;

    RepairType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @JsonValue
    public String jsonValue() {
        return code;
    }

    public static RepairType fromCode(String code) {
        for (RepairType t : values()) {
            if (t.code.equals(code)) {
                return t;
            }
        }
        return OTHER;
    }
}