package com.huashui.dormitory.domain.vo;

import com.huashui.dormitory.enums.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 我的调宿申请 VO（学生端）
 */
@Data
@Accessors(chain = true)
@Schema(description = "我的调宿申请VO")
public class MyTransferVO implements Serializable {

    @Schema(description = "申请ID")
    private Long id;

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