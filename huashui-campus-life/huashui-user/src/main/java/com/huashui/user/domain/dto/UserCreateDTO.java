package com.huashui.user.domain.dto;

import com.huashui.user.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增用户 DTO（管理员创建用户时提交的参数）。
 * <p>
 * username / password / realName / userType 为必填；
 * 密码在后端使用 BCrypt 加密存储，不会明文落库。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@Schema(description = "新增用户DTO")
public class UserCreateDTO implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名（必填）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码（必填，BCrypt加密存储）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "真实姓名（必填）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别：0-女, 1-男")
    private Boolean gender;

    @NotNull(message = "用户类型不能为空")
    @Schema(description = "用户类型（必填）", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserType userType;

    @Schema(description = "所属校区ID")
    private Long campusId;

    @Schema(description = "所属学院ID")
    private Long collegeId;

    @Schema(description = "专业名称")
    private String major;

    @Schema(description = "入学年级")
    private String grade;
}
