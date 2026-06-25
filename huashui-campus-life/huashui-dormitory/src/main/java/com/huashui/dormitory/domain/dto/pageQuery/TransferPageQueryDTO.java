package com.huashui.dormitory.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import com.huashui.dormitory.enums.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 调宿申请分页查询 DTO（管理端）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "调宿申请分页查询DTO")
public class TransferPageQueryDTO extends PageQuery {

    @Schema(description = "学生姓名（模糊）")
    private String studentName;

    @Schema(description = "原校区名称（模糊）")
    private String campusName;

    @Schema(description = "原楼栋名称（模糊）")
    private String buildingName;

    @Schema(description = "申请状态")
    private TransferStatus status;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;
}