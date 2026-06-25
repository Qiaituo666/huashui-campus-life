package com.huashui.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huashui.auth.domain.dto.LoginDTO;
import com.huashui.auth.domain.dto.RegisterDTO;
import com.huashui.auth.domain.pojo.SysUser;
import com.huashui.auth.domain.vo.CaptchaVO;
import com.huashui.auth.domain.vo.LoginVO;
import com.huashui.auth.mapper.SysUserMapper;
import com.huashui.auth.menu.RoleCode;
import com.huashui.auth.service.IAuthService;
import com.huashui.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final SysUserMapper sysUserMapper;
    private final StringRedisTemplate redisTemplate;

    private static final String CAPTCHA_PREFIX = "auth:captcha:";
    private static final long CAPTCHA_EXPIRE = 5; // 分钟



    @PostConstruct
    public void verify() {
        redisTemplate.opsForValue().set("startup:check", "ok", Duration.ofSeconds(30));
        String value = redisTemplate.opsForValue().get("startup:check");
        System.out.println("Redis 连接验证结果: " + value);
    }


    @Override
    public LoginVO login(LoginDTO dto) {
        // 1. 验证码校验
        if (StrUtil.isNotBlank(dto.getCaptchaKey())) {
            //获取缓存里的验证码
            String cachedCode = redisTemplate.opsForValue()
                    .get(CAPTCHA_PREFIX + dto.getCaptchaKey());
            if (StrUtil.isBlank(cachedCode)) {
                throw new BusinessException("验证码已过期，请刷新");
            }
            if (!cachedCode.equalsIgnoreCase(dto.getCaptchaCode())) {
                throw new BusinessException("验证码错误");
            }
            //获取后删除验证码
            redisTemplate.delete(CAPTCHA_PREFIX + dto.getCaptchaKey());
        }

        // 2. 查用户
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, dto.getUsername())
        );
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!Boolean.TRUE.equals(user.getStatus())) {
            throw new BusinessException("账号已被冻结，请联系管理员");
        }

        // 3. 验证密码 BCrypt加密存储
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 4. Sa-Token 登录
        StpUtil.login(user.getId());

        // 5. 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        sysUserMapper.updateById(user);

        // 6. 返回

        return LoginVO.builder()
                .token(StpUtil.getTokenValue())
                .userId(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .userType(user.getUserType())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public void register(RegisterDTO dto) {
        // 1. 检查用户名是否已存在
        Long count = sysUserMapper.selectCount(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, dto.getUsername())
        );
        if (count > 0) {
            throw new BusinessException("该学号已被注册");
        }

        // 2. 创建用户
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        user.setRealName(dto.getRealName());
        user.setPhone(dto.getPhone());
        user.setCollegeId(dto.getCollegeId());
        user.setMajor(dto.getMajor());
        user.setGrade(dto.getGrade());
        user.setUserType(RoleCode.STUDENT);
        user.setStatus(true);

        sysUserMapper.insert(user);
        log.info("新用户注册：{}", dto.getUsername());
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public CaptchaVO getCaptcha() {
        // 生成 4 位数字验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 50);
        String code = captcha.getCode();
        log.info("{},获取验证码:{}",LocalDateTime.now(),code);
        String key = IdUtil.fastSimpleUUID();

        // 存 Redis，5 分钟过期
        redisTemplate.opsForValue().set(
                CAPTCHA_PREFIX + key,
                code,
                CAPTCHA_EXPIRE,
                TimeUnit.MINUTES
        );

        // base64 图片（Hutool getImageBase64Data 已含 data:image/png;base64, 前缀）
        String imageBase64 = captcha.getImageBase64Data();

        return new CaptchaVO(key, imageBase64);
    }
}
