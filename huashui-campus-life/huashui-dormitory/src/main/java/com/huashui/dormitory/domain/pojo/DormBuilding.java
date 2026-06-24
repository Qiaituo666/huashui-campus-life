package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("dorm_building")
@Schema(name = "DormBuilding", description = "楼栋表")
public class DormBuilding implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "所属校区ID")
    private Long campusId;

    @Schema(description = "片区分组")
    private String area;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "楼栋编码")
    private String buildingCode;

    @Schema(description = "房型")
    private String roomType;

    @Schema(description = "总层数")
    private Integer totalFloors;

    @Schema(description = "住宿费")
    private Integer accommodationFee;

    @Schema(description = "楼栋简介")
    private String description;

    @Schema(description = "是否标准化公寓")
    private Boolean isStandardized;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    @Schema(description = "逻辑删除")
    private Boolean isDeleted;
}