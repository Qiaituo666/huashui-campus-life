package com.huashui.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.huashui.common.exception.BusinessException;
import com.huashui.common.response.Result;
import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户个人信息
 */
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final ISysUserService sysUserService;

    @GetMapping
    public Result<SysUser> getProfileInfo() {
        long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 脱敏：清除密码
        user.setPassword(null);
        return Result.ok(user);
    }

    //todo  修改密码 PUT /user/profile/password

    // todo 上传并修改头像 POST /user/profile/avatar

}
