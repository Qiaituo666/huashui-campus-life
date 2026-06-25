package com.huashui.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token Reactor 配置 —— Gateway 专用
 * SaReactorFilter 负责从请求中读取 token 并设置上下文，
 * 之后 StpUtil.checkLogin() 才能正常工作
 */
@Configuration
public class SaTokenReactorConfig {

    @Bean
    public SaReactorFilter saReactorFilter() {
        return new SaReactorFilter()
                // 拦截所有路径
                .addInclude("/**")
                // 白名单：登录、注册、验证码、文档
                .addExclude(
                        "/auth/login",
                        "/auth/logout",
                        "/auth/captcha",
                        "/auth/register",
                        "/doc.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/favicon.ico"
                )
                // 鉴权规则：所有请求都需要登录
                .setAuth(obj -> {
                    StpUtil.checkLogin();
                });
    }
}
