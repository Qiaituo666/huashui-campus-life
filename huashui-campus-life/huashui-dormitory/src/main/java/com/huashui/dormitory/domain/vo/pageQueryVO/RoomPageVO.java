package com.huashui.dormitory.domain.vo.pageQueryVO;

import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 房间分页列表 VO
 */
@Data
@Schema(description = "房间分页列表VO")
public class RoomPageVO implements Serializable {

    @Schema(description = "房间ID")
    private Long id;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "所在楼层")
    private Integer floorNumber;

    @Schema(description = "房型")
    private RoomType roomType;

    @Schema(description = "总床位数")
    private Integer totalBeds;

    @Schema(description = "已入住人数")
    private Integer occupiedBeds;

    @Schema(description = "状态：true-正常, false-维护中")
    private Boolean status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}