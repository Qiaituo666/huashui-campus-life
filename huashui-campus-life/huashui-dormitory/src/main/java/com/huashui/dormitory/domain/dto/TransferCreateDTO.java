package com.huashui.dormitory.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生提交调宿申请 DTO
 */
@Data
@Schema(description = "调宿申请创建DTO")
public class TransferCreateDTO implements Serializable {

    @Schema(description = "目标校区ID", required = true)
    private Long toCampusId;

    @Schema(description = "目标楼栋ID", required = true)
    private Long toBuildingId;

    @Schema(description = "目标房间ID", required = true)
    private Long toRoomId;

    @Schema(description = "目标床位ID（可选，不选则由系统分配）")
    private Long toBedId;

    @Schema(description = "申请理由", required = true)
    private String reason;
}