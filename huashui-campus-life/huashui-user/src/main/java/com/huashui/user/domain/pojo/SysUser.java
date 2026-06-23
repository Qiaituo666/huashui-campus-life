package com.huashui.user.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表 - 系统所有用户（学生/保洁/宿管/超级管理员）
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="用户表 - 系统所有用户（学生/保洁/宿管/超级管理员）")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名（学生为学号，教职工为工号）")
    private String username;

    @ApiModelProperty(value = "密码（BCrypt加密存储）")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号（绑定后可用于登录/找回密码）")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "性别：0-女, 1-男")
    private Boolean gender;

    @ApiModelProperty(value = "用户类型：STUDENT-学生, CLEANER-保洁, DORM_MANAGER-宿管, SUPER_ADMIN-超级管理员")
    private String userType;

    @ApiModelProperty(value = "所属校区ID（结合sys_campus.id，超级管理员可为NULL）")
    private Long campusId;

    @ApiModelProperty(value = "所属学院ID（学生必填）")
    private Long collegeId;

    @ApiModelProperty(value = "专业名称")
    private String major;

    @ApiModelProperty(value = "入学年级（如：2024）")
    private String grade;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "账号状态：0-冻结, 1-正常")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;


}
