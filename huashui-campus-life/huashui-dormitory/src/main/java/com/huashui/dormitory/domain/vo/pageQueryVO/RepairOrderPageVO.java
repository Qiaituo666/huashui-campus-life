package com.huashui.dormitory.domain.vo.pageQueryVO;

import com.huashui.dormitory.enums.RepairOrderStatus;
import com.huashui.dormitory.enums.RepairType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "报修工单分页列表VO（管理端）")
public class RepairOrderPageVO {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "工单编号")
    private String orderNo;


    @Schema(description = "楼栋ID")
    private Long buildingId;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "报修类型（PIPE/LIGHT/LOCK/AC/...）")
    private RepairType repairType;

    @Schema(description = "工单状态（PENDING/ASSIGNED/REPAIRING/COMPLETED/EVALUATED）")
    private RepairOrderStatus status;

    @Schema(description = "学生ID")
    private Long studentId;

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "维修人员ID")
    private Long repairerId;

    @Schema(description = "维修人员姓名")
    private String repairerName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "派单时间")
    private LocalDateTime assignedTime;

    @Schema(description = "维修完成时间")
    private LocalDateTime repairTime;
}