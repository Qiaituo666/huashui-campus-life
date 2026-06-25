package com.huashui.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户完整 Profile 响应
 * —— 登录后 GET /user/profile 返回
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户Profile")
public class UserProfileVO {

    @Schema(description = "基本信息")
    private UserInfo userInfo;

    @Schema(description = "角色编码列表")
    private List<String> roles;

    @Schema(description = "权限编码列表（扁平，用于按钮级判断）")
    private List<String> permissions;

    @Schema(description = "菜单树（只含 MENU 类型，用于渲染 Sidebar）")
    private List<MenuNode> menus;

    // ========== 内嵌类 ==========

    @Data
    @Accessors(chain = true)
    @Schema(description = "用户基本信息")
    public static class UserInfo {
        private Long id;
        private String username;
        private String realName;
        private String userType;
        private String phone;
        private String email;
        private String avatar;
        private Boolean gender;
        private Long campusId;
        private Long collegeId;
        private String major;
        private String grade;
    }

    @Data
    @Accessors(chain = true)
    @Schema(description = "菜单节点")
    public static class MenuNode {
        private Long id;
        private String name;
        private String code;
        private String path;
        private String component;
        private String icon;
        private Integer sortOrder;
        private List<MenuNode> children;
    }
}
