package com.huashui.user.domain.dto.pageQuery;

import com.huashui.common.domain.dto.PageQuery;
import com.huashui.user.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询 DTO。
 * <p>
 * 继承公共 PageQuery（pageNum / pageSize），补充用户表特有的筛选字段。
 * 所有筛选条件均为可选：传了就过滤，不传则忽略。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户分页查询DTO")
public class UserPageDTO extends PageQuery {

    @Schema(description = "用户名（模糊搜索）")
    private String username;

    @Schema(description = "真实姓名（模糊搜索）")
    private String realName;

    @Schema(description = "手机号（模糊搜索）")
    private String phone;

    @Schema(description = "用户类型")
    private UserType userType;

    @Schema(description = "所属校区ID")
    private Long campusId;

    @Schema(description = "所属学院ID")
    private Long collegeId;

    @Schema(description = "账号状态：0-冻结, 1-正常")
    private Boolean status;

    @Schema(description = "入学年级")
    private String grade;
}
