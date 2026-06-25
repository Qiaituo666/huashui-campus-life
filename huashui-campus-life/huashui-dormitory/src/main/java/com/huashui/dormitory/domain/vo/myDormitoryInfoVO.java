package com.huashui.dormitory.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 我的宿舍信息
 * 楼栋 + 房间 + 室友列表
 */
@Data
@Accessors(chain = true)
@Schema(name = "MyDormitoryInfoVO", description = "我的宿舍信息（楼栋+房间+室友列表）")
public class myDormitoryInfoVO implements Serializable {

    @Schema(description = "楼栋信息")
    private BuildingInfo building;

    @Schema(description = "房间信息")
    private RoomInfo room;

    @Schema(description = "室友列表")
    private List<RoomMateInfo> roommates;

    /**
     * 楼栋信息
     */
    @Data
    @Accessors(chain = true)
    @Schema(name = "BuildingInfo", description = "楼栋信息")
    public static class BuildingInfo {

        @Schema(description = "楼栋ID")
        private Long id;

        @Schema(description = "楼栋名称")
        private String buildingName;

        @Schema(description = "楼栋编码")
        private String buildingCode;

        @Schema(description = "片区")
        private String area;

        @Schema(description = "楼层数")
        private Integer totalFloors;
    }

    /**
     * 房间信息
     */
    @Data
    @Accessors(chain = true)
    @Schema(name = "RoomInfo", description = "房间信息")
    public static class RoomInfo {

        @Schema(description = "房间ID")
        private Long id;

        @Schema(description = "房间号")
        private String roomNumber;

        @Schema(description = "楼层")
        private Integer floorNumber;

        @Schema(description = "房型（四人间/六人间）")
        private String roomType;

        @Schema(description = "总床位数")
        private Integer totalBeds;

        @Schema(description = "已入住人数")
        private Integer occupiedBeds;
    }

    /**
     * 室友信息
     */
    @Data
    @Accessors(chain = true)
    @Schema(name = "RoomMateInfo", description = "室友信息")
    public static class RoomMateInfo {

        @Schema(description = "姓名")
        private String realName;

        @Schema(description = "头像URL")
        private String avatar;

        @Schema(description = "床位号")
        private String bedNumber;
    }
}