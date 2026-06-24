package com.huashui.dormitory.domain.dto;


import com.huashui.dormitory.enums.RepairType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 学生报修提交DTO
 */
@Data
@Schema(description = "学生报修提交DTO")
public class RepairCreateDTO implements Serializable {

    @Schema(description = "楼栋ID")
    private Long buildingId;

    @Schema(description = "房间ID")
    private Long roomId;

    @Schema(description = "报修类型")
    private RepairType repairType;

    @Schema(description = "问题描述")
    private String description;

    @Schema(description = "故障图片URL列表（最多3张）")
    private List<String> images;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "预约维修时间段（可选）")
    private String appointmentTime;
}