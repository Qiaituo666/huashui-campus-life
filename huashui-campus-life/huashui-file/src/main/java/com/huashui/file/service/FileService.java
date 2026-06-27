package com.huashui.file.service;

import com.huashui.common.domain.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储服务接口。
 * <p>
 * 封装 MinIO 的上传逻辑，对外只暴露文件和返回 URL，
 * 屏蔽底层 OSS 实现细节。
 * </p>
 */
public interface FileService {

    /**
     * 上传文件到 OSS。
     *
     * @param file 前端上传的文件（支持图片、文档等）
     * @return 文件访问 URL 及元信息
     */
    FileUploadVO upload(MultipartFile file);
}
