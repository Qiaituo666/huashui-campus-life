package com.huashui.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限列表 VO（平铺）。
 * <p>
 * 用于分页表格展示，不暴露逻辑删除标记。
 * 树形展示请使用 {@link PermissionTreeVO}。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@Schema(description = "权限VO")
public class PermissionVO implements Serializable {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "权限编码")
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

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
