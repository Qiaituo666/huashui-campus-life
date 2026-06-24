package com.huashui.dormitory.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "报修工单详情VO")
public class RepairOrderVO implements Serializable {

    @Schema(description = "工单ID")
    private Long id;

    @Schema(description = "工单编号")
    private String orderNo;

    @Schema(description = "报修类型")
    private String repairType;

    @Schema(description = "报修类型名称")
    private String repairTypeName;

    @Schema(description = "问题描述")
    private String description;

    @Schema(description = "故障图片")
    private List<String> images;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "预约维修时间")
    private String appointmentTime;

    @Schema(description = "工单状态")
    private String status;

    @Schema(description = "工单状态名称")
    private String statusName;

    @Schema(description = "校区名称")
    private String campusName;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "房间号")
    private String roomNo;

    @Schema(description = "维修人员ID")
    private Long repairerId;

    @Schema(description = "维修人员姓名")
    private String repairerName;

    @Schema(description = "派单时间")
    private LocalDateTime assignedTime;

    @Schema(description = "维修时间")
    private LocalDateTime repairTime;

    @Schema(description = "维修结果")
    private String repairResult;

    @Schema(description = "维修后照片")
    private List<String> repairImages;

    @Schema(description = "评分")
    private Integer rating;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}