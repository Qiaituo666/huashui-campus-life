package com.huashui.dormitory.domain.vo.pageQueryVO;

import com.huashui.dormitory.enums.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调宿申请分页列表 VO（管理端）
 */
@Data
@Schema(description = "调宿申请分页列表VO")
public class TransferPageVO implements Serializable {

    @Schema(description = "申请ID")
    private Long id;

    @Schema(description = "学生ID")
    private Long studentId;

    @Schema(description = "学生姓名")
    private String studentName;

    @Schema(description = "原校区名称")
    private String fromCampusName;

    @Schema(description = "原楼栋名称")
    private String fromBuildingName;

    @Schema(description = "原房间号")
    private String fromRoomNumber;

    @Schema(description = "原床位号")
    private String fromBedNumber;

    @Schema(description = "目标校区名称")
    private String toCampusName;

    @Schema(description = "目标楼栋名称")
    private String toBuildingName;

    @Schema(description = "目标房间号")
    private String toRoomNumber;

    @Schema(description = "目标床位号")
    private String toBedNumber;

    @Schema(description = "申请理由")
    private String reason;

    @Schema(description = "申请状态")
    private TransferStatus status;

    @Schema(description = "审批人姓名")
    private String approverName;

    @Schema(description = "审批意见")
    private String approveRemark;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}