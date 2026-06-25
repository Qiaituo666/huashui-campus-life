package com.huashui.dormitory.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 指派维修员 DTO
 */
@Data
@Schema(description = "指派维修员DTO")
public class AssignRepairDTO implements Serializable {

    @Schema(description = "维修人员ID", required = true)
    private Long repairerId;

    @Schema(description = "维修人员姓名", required = true)
    private String repairerName;
}