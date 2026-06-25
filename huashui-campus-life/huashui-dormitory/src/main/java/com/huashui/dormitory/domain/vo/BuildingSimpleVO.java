package com.huashui.dormitory.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "宿舍楼基础信息VO")
public class BuildingSimpleVO implements Serializable {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "所属校区ID（关联sys_campus.id）")
    private Long campusId;

    @Schema(description = "片区分组")
    private String area;

    @Schema(description = "楼栋名称")
    private String buildingName;

    @Schema(description = "楼栋编码")
    private String buildingCode;
}