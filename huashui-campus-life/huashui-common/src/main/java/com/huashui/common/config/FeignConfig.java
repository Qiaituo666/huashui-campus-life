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
            String token = StpUtil.getTokenValue(); // 从sa-token取出token放了请求投头
            if (StrUtil.isNotBlank(token)) {
                template.header("satoken", token);
            }
        };
    }
}