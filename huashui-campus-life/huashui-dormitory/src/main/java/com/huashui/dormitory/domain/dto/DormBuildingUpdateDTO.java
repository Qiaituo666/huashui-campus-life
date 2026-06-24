package com.huashui.dormitory.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑宿舍楼栋信息 DTO
 */
@Data
public class DormBuildingUpdateDTO implements Serializable {

    // ===== 路径参数兜底（如果你不用 @PathVariable 单独接收）=====
    private Long id;

    // ===== dorm_building =====

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


    // ===== dorm_building_config =====

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