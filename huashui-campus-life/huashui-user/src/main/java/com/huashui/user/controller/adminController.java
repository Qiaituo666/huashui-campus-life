package com.huashui.user.controller;

import com.huashui.common.domain.vo.PageVO;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.*;
import com.huashui.user.domain.dto.pageQuery.UserPageDTO;
import com.huashui.user.domain.vo.pageQueryVO.UserDetailVO;
import com.huashui.user.domain.vo.pageQueryVO.UserPageVO;
import com.huashui.user.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理（管理员端）。
 * <p>
 * 提供用户的 CRUD、状态管控、角色分配等管理功能。
 * 所有端点均需管理员权限（由 Sa-Token 全局拦截校验）。
 * </p>
 *
 * @author freedom0213
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user/admin/users")
@RequiredArgsConstructor
public class adminController {

    private final ISysUserService userService;

    /**
     * 分页查询用户列表，支持按用户名/姓名/手机/类型/校区/学院/状态/年级筛选。
     */
    @GetMapping
    @Operation(summary = "分页查询用户列表")
    public Result<PageVO<UserPageVO>> getUsers(UserPageDTO dto) {
        return userService.pageUsers(dto);
    }

    /**
     * 查询单个用户的完整信息（不含密码），同时返回用户绑定的角色 ID 列表。
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情")
    public Result<UserDetailVO> getUserDetail(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    /**
     * 管理员手动创建用户。密码使用 BCrypt 加密存储，自动校验用户名唯一性。
     */
    @PostMapping
    @Operation(summary = "新增用户（管理员创建）")
    public Result<Void> createUser(@RequestBody UserCreateDTO dto) {
        return userService.createUser(dto);
    }

    /**
     * 编辑用户的部分字段（不可修改用户名、密码、用户类型）。
     * 采用部分更新语义：传什么字段就改什么字段。
     */
    @PutMapping("/{id}")
    @Operation(summary = "编辑用户")
    public Result<Void> updateUser(@PathVariable Long id,
                                   @RequestBody UserUpdateDTO dto) {
        return userService.updateUser(id, dto);
    }

    /**
     * 逻辑删除用户（标记 is_deleted = 1，不会物理删除数据）。
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户（逻辑删除）")
    public Result<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 冻结（status = 0）或解冻（status = 1）指定用户。
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "冻结 / 解冻用户")
    public Result<Void> updateUserStatus(@PathVariable Long id,
                                         @RequestBody UserStatusDTO dto) {
        return userService.updateUserStatus(id, dto);
    }

    /**
     * 重新分配用户的角色列表（先清空旧关联，再写入新关联，全量替换）。
     */
    @PutMapping("/{id}/roles")
    @Operation(summary = "给用户批量分配角色")
    public Result<Void> assignUserRoles(@PathVariable Long id,
                                        @RequestBody UserRoleAssignDTO dto) {
        return userService.assignUserRoles(id, dto);
    }
}
