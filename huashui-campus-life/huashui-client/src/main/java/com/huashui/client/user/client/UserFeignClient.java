package com.huashui.client.user.client;

import com.huashui.client.user.domain.vo.UserSimpleVO;
import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "huashui-user", path = "/user/internal")
public interface UserFeignClient {

    /** 获取单个用户基本信息 */
    @GetMapping("/users/{id}")
    Result<UserSimpleVO> getUserById(@PathVariable Long id);

    /** 批量获取用户信息 */
    @GetMapping("/users/batch")
    Result<List<UserSimpleVO>> getUsersByIds(@RequestParam("ids") String ids);

    /** 校验用户是否存在 */
    @GetMapping("/users/{id}/exists")
    Result<Boolean> userExists(@PathVariable Long id);
}