package com.huashui.dormitory.domain.dto;

import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 编辑房间 DTO
 */
@Data
@Schema(description = "编辑房间DTO")
public class RoomUpdateDTO implements Serializable {

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "所在楼层")
    private Integer floorNumber;

    @Schema(description = "房型")
    private RoomType roomType;

    @Schema(description = "总床位数")
    private Integer totalBeds;

    @Schema(description = "房间状态：true-正常, false-维护")
    private Boolean status;

    @Schema(description = "备注")
    private String remark;
}