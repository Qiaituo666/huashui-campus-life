package com.huashui.dormitory.enums;

import lombok.Getter;

@Getter
public enum TransferStatus {

    PENDING("PENDING", "待审批"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已驳回");

    private final String code;
    private final String desc;

    TransferStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}