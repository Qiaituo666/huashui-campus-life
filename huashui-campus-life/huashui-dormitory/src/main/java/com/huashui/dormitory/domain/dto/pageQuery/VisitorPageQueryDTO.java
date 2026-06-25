package com.huashui.dormitory.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import com.huashui.dormitory.enums.VisitorStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 访客记录分页查询 DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "访客记录分页查询DTO")
public class VisitorPageQueryDTO extends PageQuery {

    @Schema(description = "访客姓名（模糊）")
    private String visitorName;

    @Schema(description = "访客手机号")
    private String visitorPhone;

    @Schema(description = "被访学生姓名（模糊）")
    private String studentName;

    @Schema(description = "访客状态")
    private VisitorStatus status;

    @Schema(description = "进入时间-开始")
    private LocalDateTime startTime;

    @Schema(description = "进入时间-结束")
    private LocalDateTime endTime;
}