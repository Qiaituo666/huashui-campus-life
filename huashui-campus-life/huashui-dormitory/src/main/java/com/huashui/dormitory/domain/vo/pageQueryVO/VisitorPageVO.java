package com.huashui.dormitory.domain.vo.pageQueryVO;

import com.huashui.dormitory.enums.VisitorStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 访客记录分页列表 VO
 */
@Data
@Schema(description = "访客记录分页列表VO")
public class VisitorPageVO implements Serializable {

    @Schema(description = "记录ID")
    private Long id;

    @Schema(description = "访客姓名")
    private String visitorName;

    @Schema(description = "访客手机号")
    private String visitorPhone;

    @Schema(description = "访客身份证号")
    private String visitorIdCard;

    @Schema(description = "被访学生ID")
    private Long studentId;

    @Schema(description = "被访学生姓名")
    private String studentName;

    @Schema(description = "被访楼栋名称")
    private String buildingName;

    @Schema(description = "被访房间号")
    private String roomNumber;

    @Schema(description = "来访事由")
    private String visitReason;

    @Schema(description = "进入时间")
    private LocalDateTime enterTime;

    @Schema(description = "离开时间")
    private LocalDateTime leaveTime;

    @Schema(description = "状态")
    private VisitorStatus status;

    @Schema(description = "登记人姓名")
    private String registrarName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}