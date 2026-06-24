package com.huashui.dormitory.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 楼栋详细信息VO（楼栋基础信息 + 配置）
 */
@Data
public class DormBuildingDetailVO implements Serializable {

    // ====== dorm_building ======

    private Long id;

    private Long campusId;

    private String area;

    private String buildingName;

    private String buildingCode;

    private String roomType;

    private Integer totalFloors;

    private Integer accommodationFee;

    private String description;

    private Integer isStandardized;

    private Integer sortOrder;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;


    // ====== dorm_building_config ======

    private Long configId;

    private Long buildingId;

    private Integer hasPrivateBath;

    private String bathType;

    private Integer hasBalcony;

    private String balconyType;

    private String bedType;

    private String floorType;

    private String hotWaterType;

    private String hotWaterHours;

    private Integer hasAc;

    private Integer hasHeating;

    private Integer hasDrinkingWater;

    private Integer hasLaundry;

    private Integer hasStudyRoom;

    private String bedSize;
}