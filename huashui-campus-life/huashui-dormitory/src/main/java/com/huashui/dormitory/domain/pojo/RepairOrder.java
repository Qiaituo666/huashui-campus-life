package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.huashui.dormitory.enums.RepairOrderStatus;
import com.huashui.dormitory.enums.RepairType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("repair_order")
public class RepairOrder {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "工单编号，例如：REP202606220001")
    private String orderNo;

    @Schema(description = "报修学生ID")
    private Long studentId;

    @Schema(description = "校区ID")
    private Long campusId;

    @Schema(description = "楼栋ID")
    private Long buildingId;

    @Schema(description = "房间ID")
    private Long roomId;

    @Schema(
            description = "报修类型",
            allowableValues = {
                    "PIPE",
                    "LIGHT",
                    "LOCK",
                    "AC",
                    "NETWORK",
                    "DOOR_WINDOW",
                    "FURNITURE",
                    "HEATING",
                    "OTHER"
            }
    )
    private RepairType repairType;

    @Schema(description = "问题描述")
    private String description;

    @Schema(description = "故障图片URL数组(JSON)")
    private String images;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "预约维修时间段")
    private String appointmentTime;

    @Schema(
            description = "工单状态",
            allowableValues = {
                    "PENDING",
                    "ASSIGNED",
                    "REPAIRING",
                    "COMPLETED",
                    "EVALUATED"
            }
    )
    private RepairOrderStatus status;

    @Schema(description = "维修人员姓名")
    private String repairerName;

    @Schema(description = "维修人员ID")
    private Long repairerId;

    @Schema(description = "派单人ID")
    private Long assignerId;

    @Schema(description = "派单时间")
    private LocalDateTime assignedTime;

    @Schema(description = "实际维修时间")
    private LocalDateTime repairTime;

    @Schema(description = "维修处理结果")
    private String repairResult;

    @Schema(description = "维修后照片URL数组(JSON)")
    private String repairImages;

    @Schema(description = "学生评分(1-5星)")
    private Integer rating;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}