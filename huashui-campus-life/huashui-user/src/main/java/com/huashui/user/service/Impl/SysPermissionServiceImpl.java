package com.huashui.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.exception.BusinessException;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.PermissionDTO;
import com.huashui.user.domain.pojo.SysPermission;
import com.huashui.user.domain.vo.PermissionTreeVO;
import com.huashui.user.domain.vo.PermissionVO;
import com.huashui.user.mapper.SysPermissionMapper;
import com.huashui.user.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限表 服务实现类。
 *
 * @author 陈会闯
 * @since 2026-06-25
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    // ==================== 权限树 ====================

    @Override
    public Result<List<PermissionTreeVO>> permissionTree() {
        // 1. 查所有启用权限，按排序号升序
        List<SysPermission> all = list(new LambdaQueryWrapper<SysPermission>()
                .eq(SysPermission::getStatus, true)
                .orderByAsc(SysPermission::getSortOrder));

        // 2. 实体列表 → 树节点列表
        List<PermissionTreeVO> nodes = all.stream()
                .map(this::toTreeVO)
                .collect(Collectors.toList());

        // 3. 按 parentId 分组，构建树
        Map<Long, List<PermissionTreeVO>> parentMap = nodes.stream()
                .collect(Collectors.groupingBy(n -> n.getParentId() != null ? n.getParentId() : 0L));

        // 4. 递归挂载 children
        for (PermissionTreeVO node : nodes) {
            node.setChildren(parentMap.getOrDefault(node.getId(), Collections.emptyList()));
        }

        // 5. 返回顶级节点（parentId = 0）
        List<PermissionTreeVO> tree = parentMap.getOrDefault(0L, Collections.emptyList());
        return Result.ok(tree);
    }

    // ==================== 权限列表（平铺/分页） ====================

    @Override
    public Result<PageVO<PermissionVO>> listPermissions() {
        // 按 parentId、sortOrder 排序（先按层级再按序号）
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysPermission::getParentId)
                .orderByAsc(SysPermission::getSortOrder);

        // 权限为系统菜单/按钮配置，数量通常不超过百条，pageSize=999 等价于全量返回
        // 同时保留 PageVO 响应结构以便前端复用分页组件
        Page<SysPermission> page = page(new Page<>(1, 999), wrapper);

        List<PermissionVO> records = page.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        PageVO<PermissionVO> pageVO = new PageVO<>();
        pageVO.setTotal(page.getTotal());
        pageVO.setPageNum(page.getCurrent());
        pageVO.setPageSize(page.getSize());
        pageVO.setRecords(records);
        return Result.ok(pageVO);
    }

    // ==================== 新增/编辑（合一） ====================

    @Override
    public Result<Void> savePermission(Long id, PermissionDTO dto) {
        if (id == null) {
            // ---- 新增 ----
            if (dto.getPermissionName() == null
                    || dto.getPermissionCode() == null
                    || dto.getPermType() == null) {
                throw new BusinessException("权限名称、编码和类型不能为空");
            }
            // 编码唯一性
            long count = count(new LambdaQueryWrapper<SysPermission>()
                    .eq(SysPermission::getPermissionCode, dto.getPermissionCode()));
            if (count > 0) {
                throw new BusinessException("权限编码已存在");
            }

            SysPermission perm = new SysPermission();
            perm.setPermissionName(dto.getPermissionName());
            perm.setPermissionCode(dto.getPermissionCode());
            perm.setParentId(dto.getParentId() != null ? dto.getParentId() : 0L);
            perm.setPermType(dto.getPermType());
            perm.setPath(dto.getPath());
            perm.setComponent(dto.getComponent());
            perm.setIcon(dto.getIcon());
            perm.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
            perm.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
            perm.setCreateTime(LocalDateTime.now());
            perm.setUpdateTime(LocalDateTime.now());
            save(perm);
        } else {
            // ---- 编辑 ----
            SysPermission perm = getById(id);
            if (perm == null) {
                throw new BusinessException("权限不存在");
            }
            SysPermission update = new SysPermission();
            update.setId(id);
            if (dto.getPermissionName() != null) update.setPermissionName(dto.getPermissionName());
            // permissionCode 忽略——编码创建后不可改
            if (dto.getParentId() != null)      update.setParentId(dto.getParentId());
            if (dto.getPermType() != null)      update.setPermType(dto.getPermType());
            if (dto.getPath() != null)          update.setPath(dto.getPath());
            if (dto.getComponent() != null)     update.setComponent(dto.getComponent());
            if (dto.getIcon() != null)          update.setIcon(dto.getIcon());
            if (dto.getSortOrder() != null)     update.setSortOrder(dto.getSortOrder());
            if (dto.getStatus() != null)        update.setStatus(dto.getStatus());
            update.setUpdateTime(LocalDateTime.now());
            updateById(update);
        }
        return Result.ok();
    }

    // ==================== 批量删除 ====================

    @Override
    public Result<Void> deletePermissions(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的权限");
        }
        // 检查每个权限是否都有子权限
        for (Long id : ids) {
            long childCount = count(new LambdaQueryWrapper<SysPermission>()
                    .eq(SysPermission::getParentId, id));
            if (childCount > 0) {
                SysPermission parent = getById(id);
                String name = parent != null ? parent.getPermissionName() : id.toString();
                throw new BusinessException("权限「" + name + "」下存在子权限，无法删除");
            }
        }
        removeByIds(ids);
        return Result.ok();
    }

    // ==================== VO 转换 ====================

    /**
     * SysPermission → PermissionVO（列表用）
     */
    private PermissionVO toVO(SysPermission p) {
        PermissionVO vo = new PermissionVO();
        vo.setId(p.getId());
        vo.setPermissionName(p.getPermissionName());
        vo.setPermissionCode(p.getPermissionCode());
        vo.setParentId(p.getParentId());
        vo.setPermType(p.getPermType());
        vo.setPath(p.getPath());
        vo.setComponent(p.getComponent());
        vo.setIcon(p.getIcon());
        vo.setSortOrder(p.getSortOrder());
        vo.setStatus(p.getStatus());
        vo.setCreateTime(p.getCreateTime());
        vo.setUpdateTime(p.getUpdateTime());
        return vo;
    }

    /**
     * SysPermission → PermissionTreeVO（树节点用，不含 children）
     */
    private PermissionTreeVO toTreeVO(SysPermission p) {
        PermissionTreeVO vo = new PermissionTreeVO();
        vo.setId(p.getId());
        vo.setPermissionName(p.getPermissionName());
        vo.setPermissionCode(p.getPermissionCode());
        vo.setParentId(p.getParentId());
        vo.setPermType(p.getPermType());
        vo.setPath(p.getPath());
        vo.setComponent(p.getComponent());
        vo.setIcon(p.getIcon());
        vo.setSortOrder(p.getSortOrder());
        vo.setStatus(p.getStatus());
        vo.setCreateTime(p.getCreateTime());
        vo.setUpdateTime(p.getUpdateTime());
        vo.setChildren(new ArrayList<>());
        return vo;
    }
}
