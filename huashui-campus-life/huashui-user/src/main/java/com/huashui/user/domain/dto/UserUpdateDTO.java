package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 编辑用户 DTO。
 * <p>
 * <b>不可修改的字段：</b>username、password、userType。
 * 仅允许修改联系人/身份/学业相关信息。
 * <br>
 * 所有字段均为可选——传 null 表示不修改该字段，实现部分更新语义。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Data
@Schema(description = "编辑用户DTO")
public class UserUpdateDTO implements Serializable {

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别：0-女, 1-男")
    private Boolean gender;

    @Schema(description = "所属校区ID")
    private Long campusId;

    @Schema(description = "所属学院ID")
    private Long collegeId;

    @Schema(description = "专业名称")
    private String major;

    @Schema(description = "入学年级")
    private String grade;
}
