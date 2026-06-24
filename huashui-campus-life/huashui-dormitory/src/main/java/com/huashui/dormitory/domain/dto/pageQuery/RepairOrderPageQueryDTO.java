package com.huashui.dormitory.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import com.huashui.dormitory.enums.RepairOrderStatus;
import com.huashui.dormitory.enums.RepairType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "报修工单分页查询条件")
public class RepairOrderPageQueryDTO extends PageQuery {

    @Schema(description = "校区名称（模糊）")
    private String campusName;

    @Schema(description = "楼栋名称（模糊）")
    private String buildingName;

    @Schema(description = "宿舍号")
    private String roomNumber;

    @Schema(description = "报修类型")
    private RepairType repairType;

    @Schema(description = "工单状态")
    private RepairOrderStatus status;

    @Schema(description = "学生姓名（模糊）")
    private String studentName;

    @Schema(description = "维修人员姓名（模糊）")
    private String repairerName;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "工单编号/手机号（模糊）")
    private String orderNo;

    @Schema(description = "是否按创建时间降序排序（true=倒序，false=正序）")
    private Boolean orderByCreateTimeDesc = true;
}