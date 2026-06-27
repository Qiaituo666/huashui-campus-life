package com.huashui.common.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应（通用）。
 * <p>
 * 供 file 模块返回、其他模块通过 Feign 接收使用。
 * 前端拿到 url 后可立即用于图片预览。
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传结果")
public class FileUploadVO {

    @Schema(description = "文件访问 URL", example = "http://127.0.0.1:9000/huashui/2026/06/abc.jpg")
    private String url;

    @Schema(description = "原始文件名", example = "avatar.jpg")
    private String originalName;

    @Schema(description = "文件大小（字节）", example = "204800")
    private Long size;
}
