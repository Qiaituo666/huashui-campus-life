package com.huashui.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限树节点 VO。
 * <p>
 * 通过 children 自嵌套形成树形结构，用于前端渲染菜单/权限树。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "权限树节点VO")
public class PermissionTreeVO implements Serializable {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "权限编码")
    private String permissionCode;

    @Schema(description = "父权限ID")
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

    @Schema(description = "子权限列表（递归嵌套）")
    private List<PermissionTreeVO> children;
}
