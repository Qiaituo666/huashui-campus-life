package com.huashui.dormitory.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum RepairOrderStatus {

    PENDING("PENDING", "待受理"),

    ASSIGNED("ASSIGNED", "已派单"),

    REPAIRING("REPAIRING", "维修中"),

    COMPLETED("COMPLETED", "已完成"),

    EVALUATED("EVALUATED", "已评价");

    @EnumValue
    private final String code;

    private final String desc;

    RepairOrderStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}