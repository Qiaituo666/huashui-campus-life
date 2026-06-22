package com.huashui.client.dormitory_api;

import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 宿舍服务 Feign 接口
 */
@FeignClient(name = "huashui-dormitory", path = "/dormitory")
public interface DormitoryFeignClient {

    @GetMapping("/room/{id}")
    Result<?> getRoomById(@PathVariable Long id);

    @GetMapping("/building/{id}")
    Result<?> getBuildingById(@PathVariable Long id);
}
