package com.huashui.auth.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.huashui.auth.menu.RoleCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
@Schema(description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码：STUDENT/CLEANER/DORM_MANAGER/SUPER_ADMIN")
    private RoleCode roleCode;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Boolean status;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;
}
