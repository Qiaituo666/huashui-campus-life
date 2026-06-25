package com.huashui.dormitory.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记访客 DTO
 */
@Data
@Schema(description = "登记访客DTO")
public class VisitorCreateDTO implements Serializable {

    @Schema(description = "访客姓名", required = true)
    private String visitorName;

    @Schema(description = "访客手机号", required = true)
    private String visitorPhone;

    @Schema(description = "访客身份证号")
    private String visitorIdCard;

    @Schema(description = "被访学生ID", required = true)
    private Long studentId;

    @Schema(description = "被访学生姓名")
    private String studentName;

    @Schema(description = "被访楼栋ID", required = true)
    private Long buildingId;

    @Schema(description = "被访房间ID", required = true)
    private Long roomId;

    @Schema(description = "来访事由")
    private String visitReason;

    @Schema(description = "备注")
    private String remark;
}