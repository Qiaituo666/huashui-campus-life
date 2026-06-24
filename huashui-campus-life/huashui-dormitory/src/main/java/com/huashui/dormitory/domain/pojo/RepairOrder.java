package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("repair_order")
public class RepairOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private Long studentId;

    private Long campusId;

    private Long buildingId;

    private Long roomId;

    private String repairType;

    private String description;

    /**
     * JSON字符串
     */
    private String images;

    private String contactPhone;

    private String appointmentTime;

    private String status;

    private String repairerName;

    private Long repairerId;

    private Long assignerId;

    private LocalDateTime assignedTime;

    private LocalDateTime repairTime;

    private String repairResult;

    /**
     * JSON字符串
     */
    private String repairImages;

    private Integer rating;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}