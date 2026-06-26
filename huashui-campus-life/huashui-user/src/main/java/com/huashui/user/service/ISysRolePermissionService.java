package com.huashui.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.user.domain.pojo.SysRolePermission;

import java.util.List;

/**
 * 角色-权限关联表 服务类。
 * <p>
 * 管理角色与权限之间的多对多关联关系，
 * 提供权限分配和查询能力。
 * </p>
 *
 * @author 陈会闯
 * @since 2026-06-23
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 查询指定角色拥有的权限 ID 列表。
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getPermissionIdsByRoleId(Long roleId);

    /**
     * 分配权限给指定角色（先删后插，全量替换）。
     * <p>
     * 传空列表表示清空该角色的所有权限。
     * </p>
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表（最终态）
     */
    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);
}
