package com.huashui.dormitory.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import com.huashui.dormitory.enums.RepairType;
import com.huashui.dormitory.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author
 */

@Data
@Schema(description = "管理端房间的分页查询的参数")
public class RoomPageDTO extends PageQuery {

    //+++++++++++++ 必选参数++++++++++++++

    @Schema(description = "所属楼栋ID")
    private Long buildingId;

    @Schema(description = "所属楼栋ID")
    private Integer floorNumber;

    // ++++++++++条件参数 ++++++++++++++

    @Schema(description = "房型：FOUR-四人间, SIX-六人间")
    private RoomType roomType;

    @Schema(description = "房间状态：0-维护中, 1-正常使用")
    private Boolean status;

    @Schema(description = "是否满员")
    private Boolean isFull;

}
