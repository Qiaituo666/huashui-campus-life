package com.huashui.user.api;


import com.huashui.client.user.domain.vo.UserSimpleVO;
import com.huashui.common.response.Result;
import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/internal")
@RequiredArgsConstructor
@MapperScan("com.huashui.user.mapper")
public class InternalUserController {

    private final SysUserMapper sysUserMapper;

    @GetMapping("/users/{id}")
    public Result<UserSimpleVO> getUserById(@PathVariable Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.ok(toVO(user));
    }

    @GetMapping("/users/batch")
    public Result<List<UserSimpleVO>> getUsersByIds(@RequestParam("ids") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::valueOf).toList();
        List<SysUser> users = sysUserMapper.selectBatchIds(idList);
        return Result.ok(users.stream().map(this::toVO).collect(Collectors.toList()));
    }

    @GetMapping("/users/{id}/exists")
    public Result<Boolean> userExists(@PathVariable Long id) {
        return Result.ok(sysUserMapper.selectById(id) != null);
    }

    private UserSimpleVO toVO(SysUser u) {
        UserSimpleVO vo = new UserSimpleVO();
        vo.setId(u.getId());
        vo.setUsername(u.getUsername());
        vo.setRealName(u.getRealName());
        vo.setPhone(u.getPhone());
        vo.setUserType(u.getUserType());
        vo.setCollegeId(u.getCollegeId());
        vo.setAvatar(u.getAvatar());
        return vo;
    }
}