package com.huashui.dormitory.domain.dto;

import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量生成房间 DTO
 */
@Data
@Schema(description = "批量生成房间DTO")
public class BatchGenerateDTO implements Serializable {

    @Schema(description = "起始楼层", required = true)
    private Integer startFloor;

    @Schema(description = "结束楼层", required = true)
    private Integer endFloor;

    @Schema(description = "每层房间数", required = true)
    private Integer roomsPerFloor;

    @Schema(description = "房型", required = true)
    private RoomType roomType;

    @Schema(description = "每间床位数", required = true)
    private Integer bedsPerRoom;
}