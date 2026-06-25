package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调宿申请表
 *
 * @author
 */
@Data
@Accessors(chain = true)
@TableName("dorm_transfer_record")
@Schema(name = "DormTransferRecord", description = "调宿申请表 - 学生申请调换宿舍记录")
public class DormTransferRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "申请人学生ID（关联sys_user.id）")
    private Long studentId;

    @Schema(description = "原校区ID")
    private Long fromCampusId;

    @Schema(description = "原楼栋ID")
    private Long fromBuildingId;

    @Schema(description = "原房间ID")
    private Long fromRoomId;

    @Schema(description = "原床位ID")
    private Long fromBedId;

    @Schema(description = "目标校区ID")
    private Long toCampusId;

    @Schema(description = "目标楼栋ID")
    private Long toBuildingId;

    @Schema(description = "目标房间ID")
    private Long toRoomId;

    @Schema(description = "目标床位ID")
    private Long toBedId;

    @Schema(description = "申请理由")
    private String reason;

    @Schema(description = "申请状态：PENDING-待审批, APPROVED-已通过, REJECTED-已驳回")
    private String status;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "审批意见")
    private String approveRemark;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "逻辑删除")
    private Boolean isDeleted;
}