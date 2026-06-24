package com.huashui.common.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignTokenInterceptor() {
        return template -> {
            String token = StpUtil.getTokenValue();
            if (StrUtil.isNotBlank(token)) {
                template.header("Authorization", token);
            }
        };
    }
}