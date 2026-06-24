package com.huashui.dormitory.domain.vo;


import com.huashui.dormitory.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 楼栋详细信息VO（楼栋基础信息 + 配置）
 */
@Data
@Schema(description = "宿舍楼栋详情VO")
public class DormBuildingDetailVO implements Serializable {

    // ====== dorm_building ======

    @Schema(description = "楼栋ID")
    private Long id;

    @Schema(description = "校区ID")
    private Long campusId;

    @Schema(description = "片区")
    private String area;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "楼栋编码")
    private String buildingCode;

    @Schema(description = "房型（四人间/六人间）")
    private RoomType roomType;

    @Schema(description = "总楼层")
    private Integer totalFloors;

    @Schema(description = "住宿费")
    private Integer accommodationFee;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "是否标准化（0否1是）")
    private Integer isStandardized;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "状态（0启用1禁用）")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    private Integer isDeleted;


    // ====== dorm_building_config ======

    @Schema(description = "配置ID")
    private Long configId;

    @Schema(description = "楼栋ID")
    private Long buildingId;

    @Schema(description = "是否独立卫浴（0否1是）")
    private Integer hasPrivateBath;

    @Schema(description = "卫浴类型")
    private BathType bathType;

    @Schema(description = "是否阳台（0否1是）")
    private Integer hasBalcony;

    @Schema(description = "阳台类型")
    private BalconyType balconyType;

    @Schema(description = "床类型")
    private BedType bedType;

    @Schema(description = "地面材质")
    private FloorType floorType;

    @Schema(description = "热水类型")
    private HotWaterType hotWaterType;

    @Schema(description = "热水供应时间")
    private String hotWaterHours;

    @Schema(description = "是否空调")
    private Integer hasAc;

    @Schema(description = "是否暖气")
    private Integer hasHeating;

    @Schema(description = "是否饮水机")
    private Integer hasDrinkingWater;

    @Schema(description = "是否洗衣房")
    private Integer hasLaundry;

    @Schema(description = "是否自习室")
    private Integer hasStudyRoom;

    @Schema(description = "床尺寸")
    private String bedSize;
}