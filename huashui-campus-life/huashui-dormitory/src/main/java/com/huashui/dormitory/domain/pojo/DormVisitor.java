package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 访客登记表
 *
 * @author
 */
@Data
@Accessors(chain = true)
@TableName("dorm_visitor")
@Schema(name = "DormVisitor", description = "访客登记表 - 外来访客登记记录")
public class DormVisitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "访客姓名")
    private String visitorName;

    @Schema(description = "访客手机号")
    private String visitorPhone;

    @Schema(description = "访客身份证号")
    private String visitorIdCard;

    @Schema(description = "被访学生ID（关联sys_user.id）")
    private Long studentId;

    @Schema(description = "被访学生姓名")
    private String studentName;

    @Schema(description = "被访校区ID")
    private Long campusId;

    @Schema(description = "被访楼栋ID")
    private Long buildingId;

    @Schema(description = "被访房间ID")
    private Long roomId;

    @Schema(description = "来访事由")
    private String visitReason;

    @Schema(description = "进入时间")
    private LocalDateTime enterTime;

    @Schema(description = "离开时间（NULL表示未离开）")
    private LocalDateTime leaveTime;

    @Schema(description = "访客状态：IN-在访, OUT-已离开")
    private String status;

    @Schema(description = "登记人ID（宿管/admin）")
    private Long registrarId;

    @Schema(description = "备注")
    private String remark;

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