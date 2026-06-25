package com.huashui.dormitory.enums;

import lombok.Getter;

@Getter
public enum VisitorStatus {

    IN("IN", "在访"),
    OUT("OUT", "已离开");

    private final String code;
    private final String desc;

    VisitorStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}