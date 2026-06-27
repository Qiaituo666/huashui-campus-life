package com.huashui.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.PermissionDTO;
import com.huashui.user.domain.pojo.SysPermission;
import com.huashui.user.domain.vo.PermissionTreeVO;
import com.huashui.user.domain.vo.PermissionVO;

import java.util.List;

/**
 * 权限表 服务类 — 管理权限定义本身（菜单/按钮/API）。
 * <p>
 * 注意：与 {@link ISysRolePermissionService} 不同，
 * 后者只管理角色-权限关联关系，本接口管理权限表的 CRUD。
 * </p>
 *
 * @author freedom0213
 * @since 2026-06-25
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 查询权限树。
     * <p>
     * 返回所有启用状态的权限，按 sortOrder 排序后构建树形结构。
     * parentId=0 为顶级节点，通过 children 递归嵌套。
     * </p>
     *
     * @return 权限树（顶级节点列表）
     */
    Result<List<PermissionTreeVO>> permissionTree();

    /**
     * 分页查询权限列表（平铺）。
     * <p>
     * 按 parentId、sortOrder 排序，用于后台表格展示。
     * </p>
     *
     * @return 分页权限列表
     */
    Result<PageVO<PermissionVO>> listPermissions();

    /**
     * 新增/编辑权限（合一）。
     * <p>
     * id == null → 新增：permissionName/permissionCode/permType 必填；
     * id != null → 编辑：permissionCode 忽略，其他字段部分更新。
     * </p>
     *
     * @param id  权限ID，null 表示新增
     * @param dto 权限表单数据
     */
    Result<Void> savePermission(Long id, PermissionDTO dto);

    /**
     * 批量删除权限。
     * <p>
     * 每个权限删除前检查是否存在子权限，任一有子权限则整体拒绝。
     * 逻辑删除，受 @TableLogic 控制。
     * </p>
     *
     * @param ids 权限ID列表
     */
    Result<Void> deletePermissions(List<Long> ids);
}
