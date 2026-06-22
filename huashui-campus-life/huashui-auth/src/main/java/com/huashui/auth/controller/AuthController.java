package com.huashui.auth.controller;

import com.huashui.common.response.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        // TODO: 实现登录逻辑
        return Result.ok("login stub");
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: 实现注销逻辑
        return Result.ok();
    }

    @GetMapping("/captcha")
    public Result<String> captcha() {
        // TODO: 生成图形验证码
        return Result.ok("captcha stub");
    }
}
