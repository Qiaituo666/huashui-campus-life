package com.huashui.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */

@RestController
@RequestMapping("/user/admin/users")
//用户管理（管理员）
public class adminController {

  // todo  分页查询用户列表 GET /user/admin/users

    // todo 查询用户详情 GET /user/admin/users/{id}

    // todo  新增用户（管理员创建）POST /user/admin/users

    // todo 编辑用户 PUT /user/admin/users/{id}

    // todo  删除用户（逻辑删除）DELETE /user/admin/users/{id}

    // todo 冻结 / 解冻用户  PUT /user/admin/users/{id}/status

    // todo  批量分配角色 PUT /user/admin/users/{id}/roles
}
