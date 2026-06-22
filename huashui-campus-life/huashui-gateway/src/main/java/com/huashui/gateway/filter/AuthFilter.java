package com.huashui.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 全局鉴权过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /** 白名单路径 */
    private static final Set<String> WHITELIST = Set.of(
            "/auth/login",
            "/auth/logout",
            "/auth/captcha",
            "/auth/register",
            "/doc.html",
            "/webjars",
            "/v3/api-docs",
            "/swagger-resources",
            "/favicon.ico"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 白名单直接放行
        for (String w : WHITELIST) {
            if (path.startsWith(w)) {
                return chain.filter(exchange);
            }
        }

        // 鉴权
        try {
            StpUtil.checkLogin();
        } catch (Exception e) {
            log.warn("[Gateway] 未登录访问: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
