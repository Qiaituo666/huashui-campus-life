package com.huashui.dormitory.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 维修完成 DTO
 */
@Data
@Schema(description = "维修完成DTO")
public class CompleteRepairDTO implements Serializable {

    @Schema(description = "维修处理结果")
    private String repairResult;

    @Schema(description = "维修后照片URL列表")
    private List<String> repairImages;
}