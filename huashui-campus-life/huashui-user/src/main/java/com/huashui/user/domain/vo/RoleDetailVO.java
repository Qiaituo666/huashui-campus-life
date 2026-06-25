package com.huashui.user.domain.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色列表 VO。
 * <p>
 * 只暴露前端需要的字段：角色名、编码、描述等，不暴露 isDeleted 逻辑删除标记。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "角色详细VO")
public class RoleDetailVO implements Serializable {

    @Schema(description = "角色拥有的权限列表")
    private List<Long> permissionIds;

    @Schema(description = "拥有此角色的用户数")
    private Integer userCount;

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "状态：0-禁用, 1-启用")
    private Boolean status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
