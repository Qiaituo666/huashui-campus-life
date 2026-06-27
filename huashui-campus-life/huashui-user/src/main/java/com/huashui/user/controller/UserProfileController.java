package com.huashui.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.huashui.client.file.client.FileFeignClient;
import com.huashui.common.domain.vo.FileUploadVO;
import com.huashui.common.exception.BusinessException;
import com.huashui.common.response.Result;
import com.huashui.user.domain.dto.AvatarUpdateDTO;
import com.huashui.user.domain.dto.PasswordUpdateDTO;
import com.huashui.user.domain.pojo.SysUser;
import com.huashui.user.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户个人信息管理（用户端）。
 * <p>
 * 所有接口均通过 Sa-Token 获取当前登录用户 ID，
 * 仅允许操作自己的数据，无权限操作他人。
 * 头像上传通过 Feign 调用 file 模块完成 OSS 存储。
 * </p>
 *
 * @author freedom0213
 */
@Tag(name = "用户个人信息管理")
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final ISysUserService sysUserService;
    private final FileFeignClient fileFeignClient;

    /**
     * 获取当前登录用户的个人信息。
     * <p>
     * 返回用户完整信息，密码字段已脱敏置空。
     * </p>
     */
    @GetMapping
    @Operation(summary = "获取用户个人信息")
    public Result<SysUser> getProfileInfo() {
        long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 脱敏：清除密码
        user.setPassword(null);
        return Result.ok(user);
    }

    /**
     * 修改密码。
     * <p>
     * 需要提供原密码以验证身份，防止 token 泄露后被直接篡改密码。
     * 新密码长度 6-20 位，会通过 BCrypt 加密后存储。
     * </p>
     */
    @PutMapping("/password")
    @Operation(summary = "修改密码")
    public Result<Void> updatePassword(@RequestBody @Valid PasswordUpdateDTO dto) {
        long userId = StpUtil.getLoginIdAsLong();
        return sysUserService.updatePassword(userId, dto);
    }

    /**
     * 上传并修改头像（一步到位）。
     * <p>
     * 前端直接上传图片文件，服务端内部通过 Feign 调用 file 模块
     * 将文件存入 MinIO，拿到 URL 后写入 {@code sys_user.avatar}。
     * </p>
     *
     * @param file 头像图片文件（支持 jpg/png/gif/webp/bmp）
     */
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传并修改头像")
    public Result<FileUploadVO> uploadAvatar(@RequestParam("file") MultipartFile file) {
        // 1. 校验文件非空
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        // 2. 校验文件类型为图片
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BusinessException("仅支持上传图片文件");
        }

        // 3. 通过 Feign 调用 file 服务上传到 OSS
        Result<FileUploadVO> uploadResult = fileFeignClient.upload(file);
        if (uploadResult == null || uploadResult.getData() == null) {
            throw new BusinessException("文件上传失败，请稍后重试");
        }
        FileUploadVO uploadVO = uploadResult.getData();

        // 4. 将 OSS URL 写入用户表的 avatar 字段
        long userId = StpUtil.getLoginIdAsLong();
        AvatarUpdateDTO dto = new AvatarUpdateDTO();
        dto.setUrl(uploadVO.getUrl());
        sysUserService.updateAvatar(userId, dto);

        // 5. 返回上传结果（含 URL，前端可直接展示）
        return Result.ok(uploadVO);
    }
}
