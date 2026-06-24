package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("dorm_room")
@Schema(name = "DormRoom", description = "房间表")
public class DormRoom implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "所属楼栋ID")
    private Long buildingId;

    @Schema(description = "房间号")
    private String roomNumber;

    @Schema(description = "所在楼层")
    private Integer floorNumber;

    @Schema(description = "房型")
    private String roomType;

    @Schema(description = "总床位数")
    private Integer totalBeds;

    @Schema(description = "已入住人数")
    private Integer occupiedBeds;

    @Schema(description = "房间状态")
    private Boolean status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "逻辑删除")
    private Boolean isDeleted;
}