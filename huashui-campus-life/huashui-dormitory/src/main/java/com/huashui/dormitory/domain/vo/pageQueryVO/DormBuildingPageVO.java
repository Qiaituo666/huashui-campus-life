package com.huashui.dormitory.domain.vo.pageQueryVO;

import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "宿舍楼分页查询结果VO")
public class DormBuildingPageVO implements Serializable {

    @Schema(description = "楼栋ID")
    private Long id;

    @Schema(description = "校区ID")
    private Long campusId;

    @Schema(description = "校区名称")
    private String campusName;

    @Schema(description = "片区")
    private String area;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "楼栋编码")
    private String buildingCode;

    @Schema(description = "房型 FOUR/SIX")
    private RoomType roomType;

    @Schema(description = "总层数")
    private Integer totalFloors;

    @Schema(description = "住宿费")
    private Integer accommodationFee;

    @Schema(description = "是否标准化 0-否 1-是")
    private Integer isStandardized;

    @Schema(description = "状态 0-停用 1-正常")
    private Integer status;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}