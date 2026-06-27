package com.huashui.user.controller;

import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.RoleFormDTO;
import com.huashui.user.domain.vo.RoleDetailVO;
import com.huashui.user.domain.vo.RoleVO;
import com.huashui.user.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理 — 对角色的 CRUD。
 * <p>
 * 角色是系统预设的身份标签（学生/保洁/宿管/超管），
 * 管理员可通过此接口管理角色定义本身，
 * 给用户分配角色则由 {@code adminController.assignUserRoles()} 负责。
 * </p>
 *
 * @author freedom0213
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/user/admin/roles")
@RequiredArgsConstructor
public class rolesController {

    private final ISysRoleService roleService;

    /**
     * 获取角色列表。
     * <p>
     * 只返回启用状态的角色，按排序号升序排列。
     * 典型用途：管理员给用户分配角色时的下拉选项。
     * </p>
     */
    @GetMapping
    @Operation(summary = "角色列表")
    public Result<List<RoleVO>> listRoles() {
        return roleService.listRoles();
    }

    /**
     * 查询角色详情。
     * <p>
     * 返回角色基本信息 + 关联的权限 ID 列表 + 拥有该角色的用户数。
     * 前端传的id就是角色表中的id
     * </p>
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询角色详细")
    public Result<RoleDetailVO> roleDetails(@PathVariable Long id) {
        return roleService.roleDetails(id);
    }

    /**
     * 新增角色（单个）。
     */
    @PostMapping
    @Operation(summary = "新增角色")
    public Result<Void> addRole(@RequestBody RoleFormDTO dto) {
        return roleService.saveRole(null, dto);
    }

    /**
     * 编辑角色。
     * <p>
     * 部分更新：传什么改什么，roleCode 创建后不可修改。
     * </p>
     */
    @PutMapping("/{id}")
    @Operation(summary = "编辑角色")
    public Result<Void> updateRole(@PathVariable Long id,
                                   @RequestBody RoleFormDTO dto) {
        return roleService.saveRole(id, dto);
    }

    /**
     * 批量删除角色（逻辑删除）。
     */
    @DeleteMapping
    @Operation(summary = "批量删除角色")
    public Result<Void> deleteRoles(@RequestBody List<Long> ids) {
        return roleService.deleteRoles(ids);
    }
}
