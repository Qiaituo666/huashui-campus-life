package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("dorm_bed")
@Schema(name = "DormBed", description = "床位表 - 房间内的具体床位")
public class DormBed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "房间ID（关联dorm_room.id）")
    private Long roomId;

    @Schema(description = "床位号（如：1号床、2号床）")
    private String bedNumber;

    @Schema(description = "入住学生ID（关联sys_user.id，空表示空床位）")
    private Long studentId;

    @Schema(description = "床位状态：0-空闲, 1-已入住, 2-预留")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}