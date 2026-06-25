package com.huashui.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.huashui.common.response.Result;
import com.huashui.user.domain.vo.UserProfileVO;
import com.huashui.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户个人信息
 */
@Tag(name = "用户Profile", description = "登录后获取当前用户的完整信息（角色+权限+菜单）")
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "获取当前用户完整 Profile")
    @GetMapping
    public Result<UserProfileVO> getProfile() {
        long userId = StpUtil.getLoginIdAsLong();
        UserProfileVO vo = userProfileService.getProfile(userId);
        return Result.ok(vo);
    }

    // todo 修改密码 PUT /user/profile/password

    // todo 上传并修改头像 POST /user/profile/avatar
}
