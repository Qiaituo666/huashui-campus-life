package com.huashui.dormitory.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 评价维修 DTO
 */
@Data
@Schema(description = "评价维修DTO")
public class RateRepairDTO implements Serializable {

    @Schema(description = "评分（1-5星）", required = true)
    private Integer rating;

    @Schema(description = "评价内容（可选）")
    private String comment;
}