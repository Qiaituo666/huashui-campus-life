package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户状态 DTO（冻结/解冻专用）。
 * <p>
 * status = true → 正常，status = false → 冻结。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "用户状态DTO")
public class UserStatusDTO implements Serializable {

    @Schema(description = "账号状态：0-冻结, 1-正常")
    private Boolean status;
}
