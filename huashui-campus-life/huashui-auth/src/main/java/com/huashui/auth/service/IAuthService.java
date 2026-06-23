package com.huashui.auth.service;

import com.huashui.auth.domain.dto.LoginDTO;
import com.huashui.auth.domain.dto.RegisterDTO;
import com.huashui.auth.domain.vo.CaptchaVO;
import com.huashui.auth.domain.vo.LoginVO;

public interface IAuthService {

    /**
     * 账号密码登录
     */
    LoginVO login(LoginDTO dto);

    /**
     * 学生注册
     */
    void register(RegisterDTO dto);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 获取图形验证码（返回base64图片）
     */
    CaptchaVO getCaptcha();
}
