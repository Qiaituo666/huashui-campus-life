package com.huashui.client.user_api;

import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务 Feign 接口
 */
@FeignClient(name = "huashui-user", path = "/user")
public interface UserFeignClient {

    @GetMapping("/{id}")
    Result<?> getUserById(@PathVariable Long id);
}
