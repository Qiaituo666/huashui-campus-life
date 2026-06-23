package com.huashui.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huashui.auth.domain.dto.LoginDTO;
import com.huashui.auth.domain.dto.RegisterDTO;
import com.huashui.auth.domain.vo.LoginVO;
import com.huashui.auth.service.IAuthService;
import com.huashui.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController 接口测试")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IAuthService authService;

    // ==================== POST /auth/login ====================
    @Nested
    @DisplayName("POST /auth/login")
    class LoginTests {

        @Test
        @DisplayName("正常登录 → 200 + token")
        void shouldReturnTokenOnSuccess() throws Exception {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");

            LoginVO vo = LoginVO.builder()
                    .token("mock-token-abc")
                    .userId(1L)
                    .username("2024001")
                    .realName("张三")
                    .userType("STUDENT")
                    .build();

            when(authService.login(any(LoginDTO.class))).thenReturn(vo);

            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.data.token").value("mock-token-abc"))
                    .andExpect(jsonPath("$.data.username").value("2024001"))
                    .andExpect(jsonPath("$.data.realName").value("张三"));
        }

        @Test
        @DisplayName("用户名为空 → 400（参数校验）")
        void shouldReturn400WhenUsernameBlank() throws Exception {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("");
            dto.setPassword("123456");

            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("密码为空 → 400（参数校验）")
        void shouldReturn400WhenPasswordBlank() throws Exception {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("");

            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("密码错误 → service 抛异常，全局异常处理器返回错误")
        void shouldReturnErrorWhenServiceThrows() throws Exception {
            LoginDTO dto = new LoginDTO();
            dto.setUsername("2024001");
            dto.setPassword("wrong");

            when(authService.login(any(LoginDTO.class)))
                    .thenThrow(new BusinessException("用户名或密码错误"));

            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(500))
                    .andExpect(jsonPath("$.message").value("用户名或密码错误"));
        }
    }

    // ==================== POST /auth/register ====================
    @Nested
    @DisplayName("POST /auth/register")
    class RegisterTests {

        @Test
        @DisplayName("正常注册 → 200")
        void shouldRegisterSuccessfully() throws Exception {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("2024002");
            dto.setPassword("654321");
            dto.setRealName("李四");

            doNothing().when(authService).register(any(RegisterDTO.class));

            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200));
        }

        @Test
        @DisplayName("学号为空 → 400")
        void shouldReturn400WhenUsernameBlank() throws Exception {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("");
            dto.setPassword("654321");
            dto.setRealName("李四");

            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("姓名为空 → 400")
        void shouldReturn400WhenRealNameBlank() throws Exception {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("2024002");
            dto.setPassword("654321");
            dto.setRealName("");

            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("学号已存在 → service 抛异常")
        void shouldReturnErrorWhenUsernameExists() throws Exception {
            RegisterDTO dto = new RegisterDTO();
            dto.setUsername("2024001");
            dto.setPassword("123456");
            dto.setRealName("张三");

            doThrow(new BusinessException("该学号已被注册"))
                    .when(authService).register(any(RegisterDTO.class));

            mockMvc.perform(post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("该学号已被注册"));
        }
    }

    // ==================== POST /auth/logout ====================
    @Test
    @DisplayName("POST /auth/logout → 200")
    void shouldLogoutSuccessfully() throws Exception {
        doNothing().when(authService).logout();

        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== GET /auth/captcha ====================
    /*@Test
    @DisplayName("GET /auth/captcha → 200 + JSON（含 captchaKey 和 base64 图片）")
    void shouldReturnCaptcha() throws Exception {
        String mockCaptchaJson = "{"captchaKey":"abc123","captchaImage":"data:image/png;base64,iVBORw0KGgo="}";
        when(authService.getCaptcha()).thenReturn(mockCaptchaJson);

        mockMvc.perform(get("/auth/captcha"))
                .andExpect(status().isOk())
                .andExpect(content().string(mockCaptchaJson));
    }*/
}
