package com.huashui.dormitory.domain.vo;

import com.huashui.dormitory.enums.BedStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 房间详情 VO（含床位分配与住客信息）
 */
@Data
@Schema(description = "房间详情VO（含床位分配情况）")
public class RoomDetailVO implements Serializable {

    @Schema(description = "房间ID")
    private Long id;

    @Schema(description = "所属楼栋ID")
    private Long buildingId;

    @Schema(description = "楼栋名称")
    private String buildingName;

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

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "床位列表（含住客信息）")
    private List<BedInfo> beds;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 床位 + 住客信息
     */
    @Data
    @Accessors(chain = true)
    @Schema(name = "BedInfo", description = "床位及住客信息")
    public static class BedInfo {

        @Schema(description = "床位ID")
        private Long bedId;

        @Schema(description = "床位号")
        private String bedNumber;

        @Schema(description = "床位状态")
        private BedStatus status;

        @Schema(description = "学生ID")
        private Long studentId;

        @Schema(description = "学生姓名")
        private String studentName;

        @Schema(description = "学号")
        private String studentNo;

        @Schema(description = "头像URL")
        private String avatar;

        @Schema(description = "入住时间")
        private LocalDateTime checkInTime;
    }
}