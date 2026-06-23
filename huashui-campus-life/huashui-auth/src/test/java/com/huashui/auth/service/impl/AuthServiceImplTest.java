package com.huashui.auth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huashui.auth.domain.dto.LoginDTO;
import com.huashui.auth.domain.dto.RegisterDTO;
import com.huashui.auth.domain.pojo.SysUser;
import com.huashui.auth.domain.vo.CaptchaVO;
import com.huashui.auth.domain.vo.LoginVO;
import com.huashui.auth.mapper.SysUserMapper;
import com.huashui.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static com.huashui.common.constants.RedisConstants.CAPTCHA_PREFIX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl 单元测试")
class AuthServiceImplTest {



    @Mock
    private SysUserMapper sysUserMapper;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private AuthServiceImpl authService;

    private SysUser testUser;

    @BeforeEach
    void setUp() {
        // 准备测试用户数据（密码用 BCrypt 加密）
        testUser = new SysUser();
        testUser.setId(1L);
        testUser.setUsername("2024001");
        testUser.setPassword(BCrypt.hashpw("123456"));
        testUser.setRealName("张三");
        testUser.setUserType("STUDENT");
        testUser.setStatus(true);
        testUser.setAvatar("https://example.com/avatar.jpg");

        // Redis  mock
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    // ==================== 登录测试 ====================
    @Nested
    @DisplayName("login - 登录")
    class LoginTests {

        @Test
        @DisplayName("正常登录：用户名密码正确，返回 token 和用户信息")
        void shouldLoginSuccessfully() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");

            when(sysUserMapper.selectOne(any(LambdaQueryWrapper.class)))
                    .thenReturn(testUser);

            LoginVO result = authService.login(dto);

            assertNotNull(result);
            assertEquals("2024001", result.getUsername());
            assertEquals("张三", result.getRealName());
            assertEquals("STUDENT", result.getUserType());
            assertNotNull(result.getToken());
        }

        @Test
        @DisplayName("用户名不存在 → 抛 BusinessException")
        void shouldThrowWhenUserNotFound() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("9999999");
            dto.setPassword("123456");

            when(sysUserMapper.selectOne(any(LambdaQueryWrapper.class)))
                    .thenReturn(null);

            assertThrows(BusinessException.class, () -> authService.login(dto));
        }

        @Test
        @DisplayName("密码错误 → 抛 BusinessException")
        void shouldThrowWhenPasswordWrong() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("wrong_password");

            when(sysUserMapper.selectOne(any(LambdaQueryWrapper.class)))
                    .thenReturn(testUser);

            assertThrows(BusinessException.class, () -> authService.login(dto));
        }

        @Test
        @DisplayName("账号已冻结 → 抛 BusinessException")
        void shouldThrowWhenAccountFrozen() {
            testUser.setStatus(false);
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");

            when(sysUserMapper.selectOne(any(LambdaQueryWrapper.class)))
                    .thenReturn(testUser);

            BusinessException ex = assertThrows(BusinessException.class,
                    () -> authService.login(dto));
            assertTrue(ex.getMessage().contains("冻结"));
        }

        @Test
        @DisplayName("带验证码登录：验证码正确 → 登录成功并删除缓存")
        void shouldLoginWithValidCaptcha() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");
            dto.setCaptchaKey("test-key");
            dto.setCaptchaCode("A3B2");

            when(valueOperations.get("auth:captcha:test-key")).thenReturn("A3B2");
            when(sysUserMapper.selectOne(any(LambdaQueryWrapper.class)))
                    .thenReturn(testUser);

            LoginVO result = authService.login(dto);

            assertNotNull(result);
            verify(redisTemplate).delete("auth:captcha:test-key");
        }

        @Test
        @DisplayName("带验证码登录：验证码过期 → 抛异常")
        void shouldThrowWhenCaptchaExpired() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");
            dto.setCaptchaKey("test-key");
            dto.setCaptchaCode("A3B2");

            when(valueOperations.get("auth:captcha:test-key")).thenReturn(null);

            BusinessException ex = assertThrows(BusinessException.class,
                    () -> authService.login(dto));
            assertTrue(ex.getMessage().contains("过期"));
        }

        @Test
        @DisplayName("带验证码登录：验证码错误 → 抛异常")
        void shouldThrowWhenCaptchaWrong() {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");
            dto.setCaptchaKey("test-key");
            dto.setCaptchaCode("WRONG");

            when(valueOperations.get("auth:captcha:test-key")).thenReturn("A3B2");

            BusinessException ex = assertThrows(BusinessException.class,
                    () -> authService.login(dto));
            assertTrue(ex.getMessage().contains("验证码错误"));
        }
    }

    // ==================== 注册测试 ====================
    @Nested
    @DisplayName("register - 注册")
    class RegisterTests {

        @Test
        @DisplayName("正常注册：创建用户，密码 BCrypt 加密")
        void shouldRegisterSuccessfully() {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("2024002");
            dto.setPassword("654321");
            dto.setRealName("李四");
            dto.setPhone("13800138000");
            dto.setCollegeId(1L);
            dto.setMajor("软件工程");
            dto.setGrade("2024");

            when(sysUserMapper.selectCount(any(LambdaQueryWrapper.class)))
                    .thenReturn(0L);
            when(sysUserMapper.insert(any(SysUser.class))).thenReturn(1);

            authService.register(dto);

            ArgumentCaptor<SysUser> captor = ArgumentCaptor.forClass(SysUser.class);
            verify(sysUserMapper).insert(captor.capture());

            SysUser saved = captor.getValue();
            assertEquals("2024002", saved.getUsername());
            assertEquals("李四", saved.getRealName());
            assertEquals("STUDENT", saved.getUserType());
            assertTrue(saved.getStatus());
            // 密码已加密
            assertNotEquals("654321", saved.getPassword());
            assertTrue(BCrypt.checkpw("654321", saved.getPassword()));
        }

        @Test
        @DisplayName("学号已被注册 → 抛异常")
        void shouldThrowWhenUsernameExists() {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");
            dto.setRealName("张三");

            when(sysUserMapper.selectCount(any(LambdaQueryWrapper.class)))
                    .thenReturn(1L);

            BusinessException ex = assertThrows(BusinessException.class,
                    () -> authService.register(dto));
            assertTrue(ex.getMessage().contains("已被注册"));
        }
    }

    // ==================== 登出测试 ====================
    @Test
    @DisplayName("logout - 正常退出")
    void shouldLogout() {
        // StpUtil.logout() 是静态方法，不抛异常即成功
        assertDoesNotThrow(() -> authService.logout());
    }



    // ==================== 验证码测试 ====================
    @Test
    @DisplayName("getCaptcha - 生成验证码并缓存到 Redis")
    void shouldGenerateCaptcha() {
        CaptchaVO result = authService.getCaptcha();
        String cachedCode = redisTemplate.opsForValue()
                .get(CAPTCHA_PREFIX + result.getCaptchaKey());
        log.info("Redis验证码:{}",cachedCode);
    }
}
