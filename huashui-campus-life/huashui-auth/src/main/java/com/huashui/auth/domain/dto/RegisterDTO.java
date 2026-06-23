package com.huashui.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "注册请求")
public class RegisterDTO {

    @Schema(description = "学号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "学号不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "学院ID")
    private Long collegeId;

    @Schema(description = "专业名称")
    private String major;

    @Schema(description = "入学年级")
    private String grade;
}
