package com.huashui.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.user.domain.pojo.SysRolePermission;
import com.huashui.user.mapper.SysRolePermissionMapper;
import com.huashui.user.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色-权限关联表 服务实现类。
 *
 * @author freedom0213
 * @since 2026-06-23
 */
@Service
public class SysRolePermissionServiceImpl
        extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements ISysRolePermissionService {

    /**
     * 通过角色id获取权限ID集合
     * @param roleId 角色ID
     * @return 返回权限ID集合
     */
    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
        return list(new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, roleId))
                .stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色ID分配权限
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表（最终态）
     */
    @Override
    public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        // 1. 清除旧关联
        remove(new LambdaQueryWrapper<SysRolePermission>()
                .eq(SysRolePermission::getRoleId, roleId));

        // 2. 写入新关联（空列表 = 清空）
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<SysRolePermission> list = permissionIds.stream()
                    .map(permId -> new SysRolePermission()
                            .setRoleId(roleId)
                            .setPermissionId(permId))
                    .collect(Collectors.toList());
            saveBatch(list);
        }
    }
}
