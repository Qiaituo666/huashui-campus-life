package com.huashui.user.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.huashui.user.menu.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表 - 系统所有用户（学生/保洁/宿管/超级管理员）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@Schema(name = "SysUser", description = "用户表 - 系统所有用户（学生/保洁/宿管/超级管理员）")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名（学生为学号，教职工为工号）")
    private String username;

    @Schema(description = "密码（BCrypt加密存储）")
    private String password;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号（绑定后可用于登录/找回密码）")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "性别：0-女, 1-男")
    private Boolean gender;

    @Schema(description = "用户类型：STUDENT-学生, CLEANER-保洁, DORM_MANAGER-宿管, SUPER_ADMIN-超级管理员")
    private UserType userType;

    @Schema(description = "所属校区ID（超级管理员可为空）")
    private Long campusId;

    @Schema(description = "所属学院ID（学生必填）")
    private Long collegeId;

    @Schema(description = "专业名称")
    private String major;

    @Schema(description = "入学年级（如：2024）")
    private String grade;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "账号状态：0-冻结, 1-正常")
    private Boolean status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    private Boolean isDeleted;
}