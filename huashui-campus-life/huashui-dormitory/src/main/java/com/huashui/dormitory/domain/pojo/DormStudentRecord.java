package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生住宿记录表
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dorm_student_record")
@Schema(name = "DormStudentRecord", description = "学生住宿记录表 - 记录学生的入住/退宿历史")
public class DormStudentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "学生用户ID（关联sys_user.id）")
    private Long studentId;

    @Schema(description = "校区ID（关联sys_campus.id）")
    private Long campusId;

    @Schema(description = "楼栋ID（关联dorm_building.id）")
    private Long buildingId;

    @Schema(description = "房间ID（关联dorm_room.id）")
    private Long roomId;

    @Schema(description = "床位ID（关联dorm_bed.id）")
    private Long bedId;

    @Schema(description = "入住时间")
    private LocalDateTime checkInTime;

    @Schema(description = "退宿时间（NULL表示仍在住）")
    private LocalDateTime checkOutTime;

    @Schema(description = "住宿状态：0-已退宿, 1-在住")
    private Boolean status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}