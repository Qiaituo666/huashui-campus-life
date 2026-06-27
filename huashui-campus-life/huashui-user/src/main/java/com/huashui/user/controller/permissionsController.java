package com.huashui.user.controller;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.PermissionDTO;
import com.huashui.user.domain.vo.PermissionTreeVO;
import com.huashui.user.domain.vo.PermissionVO;
import com.huashui.user.service.ISysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理 — 对权限定义（菜单/按钮/API）的 CRUD。
 * <p>
 * 权限采用树形结构，parentId=0 为顶级节点。
 * 将权限分配给角色由角色管理模块负责。
 * </p>
 *
 * @author freedom0213
 */
@Tag(name = "用户权限管理")
@RestController
@RequestMapping("/user/admin/permissions")
@RequiredArgsConstructor
public class permissionsController {

    private final ISysPermissionService permissionService;

    /**
     * 查询权限树。
     * <p>
     * 返回树形结构（父子递归），不需要分页，用于菜单配置页。
     * </p>
     */
    @GetMapping("/tree")
    @Operation(summary = "查询权限树")
    public Result<List<PermissionTreeVO>> permissionsTree() {
        return permissionService.permissionTree();
    }

    /**
     * 查询权限列表（平铺分页）。
     * <p>
     * 按父级、排序号排列，用于后台表格展示。
     * </p>
     */
    @GetMapping
    @Operation(summary = "查询权限列表")
    public Result<PageVO<PermissionVO>> listPermissions() {
        return permissionService.listPermissions();
    }

    /**
     * 新增权限（单个）。
     * <p>
     * permissionName / permissionCode / permType 必填。
     * </p>
     */
    @PostMapping
    @Operation(summary = "新增权限")
    public Result<Void> addPermission(@RequestBody PermissionDTO dto) {
        return permissionService.savePermission(null, dto);
    }

    /**
     * 编辑权限。
     * <p>
     * 部分更新：传什么改什么，permissionCode 创建后不可修改。
     * </p>
     */
    @PutMapping("/{id}")
    @Operation(summary = "编辑权限")
    public Result<Void> updatePermission(@PathVariable Long id,
                                         @RequestBody PermissionDTO dto) {
        return permissionService.savePermission(id, dto);
    }

    /**
     * 批量删除权限。
     * <p>
     * 有子权限时禁止删除，返回错误提示。
     * </p>
     */
    @DeleteMapping
    @Operation(summary = "批量删除权限")
    public Result<Void> deletePermissions(@RequestBody List<Long> ids) {
        return permissionService.deletePermissions(ids);
    }
}
