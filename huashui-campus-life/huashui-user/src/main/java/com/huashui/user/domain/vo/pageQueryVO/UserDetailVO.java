package com.huashui.user.domain.vo.pageQueryVO;

import com.huashui.user.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情 VO。
 * <p>
 * 包含用户全量信息（不含密码），额外附带 roleIds 表示用户绑定的角色列表。
 * 用于管理员查看单个用户详情。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@Schema(description = "用户详情VO")
public class UserDetailVO implements Serializable {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

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

    @Schema(description = "用户类型")
    private UserType userType;

    @Schema(description = "所属校区ID")
    private Long campusId;

    @Schema(description = "所属学院ID")
    private Long collegeId;

    @Schema(description = "专业名称")
    private String major;

    @Schema(description = "入学年级")
    private String grade;

    @Schema(description = "账号状态：0-冻结, 1-正常")
    private Boolean status;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "角色ID列表（用户绑定的角色）")
    private List<Long> roleIds;
}
