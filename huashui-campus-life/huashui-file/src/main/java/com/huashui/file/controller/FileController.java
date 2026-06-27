package com.huashui.file.controller;

import com.huashui.common.response.Result;
import com.huashui.common.domain.vo.FileUploadVO;
import com.huashui.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理 — 通用文件上传。
 * <p>
 * 提供文件上传到 OSS 的能力，返回可访问的 URL。
 * 典型调用链：前端选图 → 本接口上传 → 拿到 URL 预览 → 用户确认 → 业务模块入库。
 * </p>
 *
 * @author freedom0213
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 上传文件到 OSS。
     * <p>
     * 支持 jpg/png/gif/webp/bmp 格式，大小限制由
     * {@code spring.servlet.multipart.max-file-size} 控制（当前 10MB）。
     * </p>
     *
     * @param file 上传的文件
     * @return 文件 URL + 元信息，前端可用 url 字段直接预览
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result<FileUploadVO> upload(@RequestParam("file") MultipartFile file) {
        return Result.ok(fileService.upload(file));
    }
}
