package com.huashui.client.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户基础信息VO")
public class UserSimpleVO implements Serializable {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户类型")
    private String userType;

    @Schema(description = "学院ID")
    private Long collegeId;

    @Schema(description = "头像地址")
    private String avatar;
}