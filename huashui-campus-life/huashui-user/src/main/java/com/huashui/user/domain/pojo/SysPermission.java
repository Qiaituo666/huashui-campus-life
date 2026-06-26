package com.huashui.user.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表 — 系统菜单/按钮/接口权限。
 * <p>
 * 树形结构：parentId = 0 为顶级节点，通过 parentId 建立父子关系。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@Schema(description = "权限表")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "权限编码（例：user:view, room:assign）")
    private String permissionCode;

    @Schema(description = "父权限ID（0表示顶级）")
    private Long parentId;

    @Schema(description = "权限类型：MENU-菜单，BUTTON-按钮，API-接口")
    private String permType;

    @Schema(description = "前端路由路径")
    private String path;

    @Schema(description = "前端组件路径")
    private String component;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Boolean status;

    // 注：createTime / updateTime 在 Service 层手动设置，
    //     项目未配置 MetaObjectHandler，故不使用 @TableField(fill = ...)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;
}
