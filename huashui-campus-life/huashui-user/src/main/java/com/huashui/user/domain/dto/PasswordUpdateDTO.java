package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改密码请求。
 * <p>
 * 需要提供原密码以验证身份，防止 token 被窃取后直接篡改密码。
 * </p>
 *
 * @author freedom0213
 */
@Data
@Schema(description = "修改密码")
public class PasswordUpdateDTO {

    @NotBlank(message = "原密码不能为空")
    @Schema(description = "原密码（用于身份验证）", example = "old123456")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    @Schema(description = "新密码", example = "new123456")
    private String newPassword;
}
