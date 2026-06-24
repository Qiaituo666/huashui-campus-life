package com.huashui.dormitory.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 楼栋条件分页查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "楼栋条件分页查询DTO")
public class DormBuildingPageDTO extends PageQuery {

    @Schema(description = "校区名称") // 根据上一步的所在城市查询该城市的校区为下拉菜单,没有则查询所有校区
    private Long campus_name;

    @Schema(description = "片区")  //根据校区,查询该校区的片区,然后下拉菜单用户选择 ,还未选择校区时查询所有片区
    private String area;

    @Schema(description = "楼栋名称") //模糊查询
    private String buildingName;

    @Schema(description = "楼栋编码") //根据校区查询楼栋编码为下拉菜单
    private String buildingCode;

    @Schema(description = "状态：0-停用，1-正常")
    private Integer status;

    @Schema(description = "是否标准化宿舍：0-否，1-是")
    private Integer isStandardized;

    @Schema(description = "所在城市")  // 查询所有城市为下拉菜单供用户选择

    private String  location;

}