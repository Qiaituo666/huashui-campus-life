package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量分配角色 DTO。
 * <p>
 * 后端采用"先删后插"策略：传入的 roleIds 列表即为该用户的最终角色集合。
 * 传空列表表示清空该用户所有角色。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@Schema(description = "批量分配角色DTO")
public class UserRoleAssignDTO implements Serializable {

    @Schema(description = "角色ID列表（最终态，全量替换）")
    private List<Long> roleIds;
}
