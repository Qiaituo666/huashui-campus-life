package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表单 DTO — 新增和编辑共用。
 * <p>
 * 新增时 permissionName / permissionCode / permType 为必填；
 * 编辑时所有字段均可选，permissionCode 创建后不可修改。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "权限表单DTO（新增/编辑共用）")
public class PermissionDTO implements Serializable {

    @Schema(description = "权限名称（新增必填）")
    private String permissionName;

    @Schema(description = "权限编码，例：user:view（新增必填，编辑忽略）")
    private String permissionCode;

    @Schema(description = "父权限ID（0表示顶级）")
    private Long parentId;

    @Schema(description = "权限类型：MENU-菜单，BUTTON-按钮，API-接口（新增必填）")
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
}
