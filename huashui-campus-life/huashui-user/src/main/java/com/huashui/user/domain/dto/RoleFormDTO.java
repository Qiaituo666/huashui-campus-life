package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表单 DTO — 新增和编辑共用。
 * <p>
 * 新增时 roleName / roleCode 为必填；
 * 编辑时所有字段均为可选，roleCode 传了也会被忽略（编码创建后不可改）。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "角色表单DTO（新增/编辑共用）")
public class RoleFormDTO implements Serializable {

    @Schema(description = "角色名称（新增必填）")
    private String roleName;

    @Schema(description = "角色编码：STUDENT/CLEANER/DORM_MANAGER/SUPER_ADMIN（新增必填，编辑忽略）")
    private String roleCode;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Boolean status;
}
