package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户状态 DTO（冻结/解冻专用）。
 * <p>
 * status = true → 正常，status = false → 冻结。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@Schema(description = "用户状态DTO")
public class UserStatusDTO implements Serializable {

    @NotNull(message = "状态不能为空")
    @Schema(description = "账号状态：0-冻结, 1-正常", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean status;
}
