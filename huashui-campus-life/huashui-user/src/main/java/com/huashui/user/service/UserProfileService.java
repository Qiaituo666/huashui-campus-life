package com.huashui.user.service;

import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.domain.pojo.SysRole;
import com.huashui.user.domain.pojo.SysPermission;
import com.huashui.user.domain.vo.UserProfileVO;
import com.huashui.user.mapper.SysRoleMapper;
import com.huashui.user.mapper.SysPermissionMapper;
import com.huashui.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 Profile 服务
 * —— 组装：基本信息 + 角色 + 权限 + 菜单树
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    /**
     * 获取用户完整 Profile
     */
    public UserProfileVO getProfile(Long userId) {
        // ① 用户基本信息
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new com.huashui.common.exception.BusinessException("用户不存在");
        }

        UserProfileVO.UserInfo userInfo = new UserProfileVO.UserInfo()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setRealName(user.getRealName())
                .setUserType(user.getUserType() != null ? user.getUserType().name() : null)
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setAvatar(user.getAvatar())
                .setGender(user.getGender())
                .setCampusId(user.getCampusId())
                .setCollegeId(user.getCollegeId())
                .setMajor(user.getMajor())
                .setGrade(user.getGrade());

        // ② 角色列表
        List<SysRole> roles = sysRoleMapper.selectByUserId(userId);
        List<String> roleCodes = roles.stream()
                .map(SysRole::getRoleCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // ③ 权限（扁平，用于前端 v-if 判断按钮）
        List<SysPermission> permissions = roles.isEmpty()
                ? Collections.emptyList()
                : sysPermissionMapper.selectByRoleIds(
                    roles.stream().map(SysRole::getId).collect(Collectors.toList()));

        List<String> permissionCodes = permissions.stream()
                .map(SysPermission::getPermissionCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // ④ 菜单树（只取 MENU 类型，构建递归结构）
        List<UserProfileVO.MenuNode> menus = buildMenuTree(permissions);

        return new UserProfileVO()
                .setUserInfo(userInfo)
                .setRoles(roleCodes)
                .setPermissions(permissionCodes)
                .setMenus(menus);
    }

    /**
     * 将 MENU 类型权限构建为树形结构
     */
    private List<UserProfileVO.MenuNode> buildMenuTree(List<SysPermission> allPerms) {
        List<SysPermission> menuPerms = allPerms.stream()
                .filter(p -> "MENU".equals(p.getPermType()) && Boolean.TRUE.equals(p.getStatus()))
                .sorted(Comparator.comparingInt(p -> p.getSortOrder() != null ? p.getSortOrder() : 0))
                .collect(Collectors.toList());

        // 根节点 (parentId == 0)
        List<UserProfileVO.MenuNode> roots = menuPerms.stream()
                .filter(p -> p.getParentId() == null || p.getParentId() == 0)
                .map(this::toMenuNode)
                .collect(Collectors.toList());

        // 递归挂载子节点
        for (UserProfileVO.MenuNode root : roots) {
            attachChildren(root, menuPerms);
        }
        return roots;
    }

    private UserProfileVO.MenuNode toMenuNode(SysPermission p) {
        UserProfileVO.MenuNode node = new UserProfileVO.MenuNode()
                .setId(p.getId())
                .setName(p.getPermissionName())
                .setCode(p.getPermissionCode())
                .setPath(p.getPath())
                .setComponent(p.getComponent())
                .setIcon(p.getIcon())
                .setSortOrder(p.getSortOrder());
        node.setChildren(new ArrayList<>());
        return node;
    }

    private void attachChildren(UserProfileVO.MenuNode parent, List<SysPermission> all) {
        List<UserProfileVO.MenuNode> children = all.stream()
                .filter(p -> p.getParentId() != null && p.getParentId().equals(parent.getId()))
                .map(this::toMenuNode)
                .collect(Collectors.toList());
        parent.setChildren(children);
        for (UserProfileVO.MenuNode child : children) {
            attachChildren(child, all); // 递归更深层级
        }
    }
}
