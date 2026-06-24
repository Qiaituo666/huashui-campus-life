package com.huashui.user.controller;

import com.huashui.common.response.Result;
import com.huashui.user.domain.pojo.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    // todo 从 Sa-Token 上下文中获取当前登录用户 ID，返回完整个人信息（含 roles 和 permissions 列表，供前端渲染菜单）
    @GetMapping("/user/profile")
    public Result<SysUser> getProfileInfo(){
        return null;
    }

    //todo  修改密码 PUT /user/profile/password


    // todo 上传并修改头像 POST /user/profile/avatar


}
