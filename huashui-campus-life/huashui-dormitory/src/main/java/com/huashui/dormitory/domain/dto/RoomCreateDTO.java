package com.huashui.dormitory.domain.dto;

import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增房间 DTO
 */
@Data
@Schema(description = "新增房间DTO")
public class RoomCreateDTO implements Serializable {

    @Schema(description = "所属楼栋ID", required = true)
    private Long buildingId;

    @Schema(description = "房间号", required = true)
    private String roomNumber;

    @Schema(description = "所在楼层", required = true)
    private Integer floorNumber;

    @Schema(description = "房型")
    private RoomType roomType;

    @Schema(description = "总床位数")
    private Integer totalBeds;

    @Schema(description = "备注")
    private String remark;
}