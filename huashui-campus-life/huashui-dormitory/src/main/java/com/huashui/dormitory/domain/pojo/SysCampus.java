package com.huashui.dormitory.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_campus")
@Schema(description = "校区表")
public class SysCampus implements Serializable {

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "校区名称（龙子湖校区/花园校区/江淮校区）")
    private String campusName;

    @Schema(description = "校区编码（LZH/HY/JH）")
    private String campusCode;

    @Schema(description = "所在城市")
    private String location;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态：0禁用 1启用")
    private Boolean status;

    @Schema(description = "逻辑删除：0未删除 1已删除")
    private Boolean isDeleted;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}