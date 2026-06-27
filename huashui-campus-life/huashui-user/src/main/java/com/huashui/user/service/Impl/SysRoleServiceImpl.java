package com.huashui.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.exception.BusinessException;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.RoleFormDTO;
import com.huashui.user.domain.pojo.SysRole;
import com.huashui.user.domain.pojo.SysUserRole;
import com.huashui.user.domain.vo.RoleDetailVO;
import com.huashui.user.domain.vo.RoleVO;
import com.huashui.user.mapper.SysRoleMapper;
import com.huashui.user.service.ISysRolePermissionService;
import com.huashui.user.service.ISysRoleService;
import com.huashui.user.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表 服务实现类。
 *
 * @author freedom0213
 * @since 2026-06-25
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final ISysRolePermissionService rolePermissionService;
    private final ISysUserRoleService userRoleService;

    // ==================== 角色列表 ====================

    @Override
    public Result<List<RoleVO>> listRoles() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getStatus, true)
                .orderByAsc(SysRole::getSortOrder);

        List<SysRole> roles = list(wrapper);
        List<RoleVO> voList = roles.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    // ==================== 角色详情 ====================

    @Override
    public Result<RoleDetailVO> roleDetails(Long id) {
        // 1. 查角色基本信息
        SysRole role = getById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 2. 基础字段 → VO
        RoleDetailVO vo = toDetailVO(role);

        // 3. 查该角色绑定的权限 ID 列表（通过关联 Service）
        List<Long> permissionIds = rolePermissionService.getPermissionIdsByRoleId(id);
        vo.setPermissionIds(permissionIds);

        // 4. 统计拥有该角色的用户数（通过关联 Service）
        long userCount = userRoleService.count(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getRoleId, id));
        vo.setUserCount(userCount);

        return Result.ok(vo);
    }

    // ==================== 新增/编辑（合一） ====================

    @Override
    public Result<Void> saveRole(Long id, RoleFormDTO dto) {
        if (id == null) {
            // ---- 新增 ----
            if (dto.getRoleName() == null || dto.getRoleCode() == null) {
                throw new BusinessException("角色名称和编码不能为空");
            }
            // 编码唯一性校验
            long count = count(new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getRoleCode, dto.getRoleCode()));
            if (count > 0) {
                throw new BusinessException("角色编码已存在");
            }

            SysRole role = new SysRole();
            role.setRoleName(dto.getRoleName());
            role.setRoleCode(dto.getRoleCode());
            role.setDescription(dto.getDescription());
            role.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
            role.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
            role.setCreateTime(LocalDateTime.now());
            role.setUpdateTime(LocalDateTime.now());
            save(role);
        } else {
            // ---- 编辑 ----
            SysRole role = getById(id);
            if (role == null) {
                throw new BusinessException("角色不存在");
            }
            // 部分更新：传什么改什么，roleCode 忽略
            SysRole updateRole = new SysRole();
            updateRole.setId(id);
            if (dto.getRoleName() != null)   updateRole.setRoleName(dto.getRoleName());
            if (dto.getDescription() != null) updateRole.setDescription(dto.getDescription());
            if (dto.getSortOrder() != null)  updateRole.setSortOrder(dto.getSortOrder());
            if (dto.getStatus() != null)     updateRole.setStatus(dto.getStatus());
            updateRole.setUpdateTime(LocalDateTime.now());
            updateById(updateRole);
        }
        return Result.ok();
    }

    // ==================== 批量删除 ====================

    /**
     * 批量删除角色（逻辑删除）。
     * <p>
     * 删除前逐一检查每个角色是否仍有用户绑定，
     * 任一角色有用户在使用则整体拒绝，防止产生孤立关联记录。
     * </p>
     */
    @Override
    public Result<Void> deleteRoles(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的角色");
        }
        // 检查每个角色下是否仍有用户绑定
        for (Long id : ids) {
            long userCount = userRoleService.count(
                    new LambdaQueryWrapper<SysUserRole>()
                            .eq(SysUserRole::getRoleId, id));
            if (userCount > 0) {
                SysRole role = getById(id);
                String name = role != null ? role.getRoleName() : id.toString();
                throw new BusinessException("角色「" + name + "」下还有 " + userCount + " 个用户，无法删除");
            }
        }
        // 逻辑删除：MyBatis Plus 的 removeByIds 受 @TableLogic 控制
        removeByIds(ids);
        return Result.ok();
    }

    // ==================== VO 转换 ====================

    /**
     * SysRole → RoleVO（列表用）
     */
    private RoleVO toVO(SysRole r) {
        RoleVO vo = new RoleVO();
        vo.setId(r.getId());
        vo.setRoleName(r.getRoleName());
        vo.setRoleCode(r.getRoleCode());
        vo.setDescription(r.getDescription());
        vo.setSortOrder(r.getSortOrder());
        vo.setStatus(r.getStatus());
        vo.setCreateTime(r.getCreateTime());
        vo.setUpdateTime(r.getUpdateTime());
        return vo;
    }

    /**
     * SysRole → RoleDetailVO（详情用，不含关联数据）
     */
    private RoleDetailVO toDetailVO(SysRole r) {
        RoleDetailVO vo = new RoleDetailVO();
        vo.setId(r.getId());
        vo.setRoleName(r.getRoleName());
        vo.setRoleCode(r.getRoleCode());
        vo.setDescription(r.getDescription());
        vo.setSortOrder(r.getSortOrder());
        vo.setStatus(r.getStatus());
        vo.setCreateTime(r.getCreateTime());
        vo.setUpdateTime(r.getUpdateTime());
        vo.setPermissionIds(Collections.emptyList());
        vo.setUserCount(0L);
        return vo;
    }
}
