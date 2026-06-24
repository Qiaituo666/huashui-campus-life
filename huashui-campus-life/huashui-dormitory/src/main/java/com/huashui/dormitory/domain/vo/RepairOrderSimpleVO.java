package com.huashui.dormitory.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "我的报修列表VO")
public class RepairOrderSimpleVO {

    @Schema(description = "工单ID")
    private Long id;

    @Schema(description = "工单编号")
    private String orderNo;

    @Schema(description = "报修类型")
    private String repairType;

    @Schema(description = "报修类型名称")
    private String repairTypeName;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "房间号")
    private String roomNo;

    @Schema(description = "工单状态")
    private String status;

    @Schema(description = "工单状态名称")
    private String statusName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}