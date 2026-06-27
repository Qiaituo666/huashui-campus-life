package com.huashui.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改头像请求。
 * <p>
 * 前端先调用文件服务上传拿到 OSS URL，
 * 用户确认后再将 URL 传给本接口入库。
 * </p>
 *
 * @author freedom0213
 */
@Data
@Schema(description = "修改头像")
public class AvatarUpdateDTO {

    @NotBlank(message = "头像URL不能为空")
    @Schema(description = "OSS 返回的头像文件 URL", example = "http://127.0.0.1:9000/huashui/avatars/abc.jpg")
    private String url;
}
