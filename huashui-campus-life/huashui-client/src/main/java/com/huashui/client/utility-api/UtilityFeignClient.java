package com.huashui.client.utility_api;

import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 水电服务 Feign 接口
 */
@FeignClient(name = "huashui-utility", path = "/utility")
public interface UtilityFeignClient {

    @GetMapping("/water/balance/{roomId}")
    Result<?> getWaterBalance(@PathVariable Long roomId);

    @GetMapping("/electric/balance/{roomId}")
    Result<?> getElectricBalance(@PathVariable Long roomId);
}
