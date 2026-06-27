package com.huashui.file.service.Impl;

import com.huashui.common.exception.BusinessException;
import com.huashui.common.domain.vo.FileUploadVO;
import com.huashui.file.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

/**
 * 文件存储服务实现 — MinIO。
 * <p>
 * 上传流程：
 * <ol>
 *   <li>校验文件是否为空</li>
 *   <li>校验文件扩展名是否在白名单内</li>
 *   <li>按日期分目录生成唯一文件名</li>
 *   <li>通过 MinioClient 上传到指定 Bucket</li>
 *   <li>拼接访问 URL 返回</li>
 * </ol>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.endpoint}")
    private String endpoint;

    /** 允许上传的文件扩展名 */
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif", "webp", "bmp"
    );

    @Override
    public FileUploadVO upload(MultipartFile file) {
        // 1. 参数校验
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 2. 扩展名校验
        String originalName = file.getOriginalFilename();
        String extension = getExtension(originalName);
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new BusinessException("不支持的文件类型，仅允许：" + ALLOWED_EXTENSIONS);
        }

        // 3. 生成对象名：日期目录 + UUID + 扩展名（方便按天清理）
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String objectName = datePath + "/" + UUID.randomUUID() + "." + extension.toLowerCase();

        try {
            // 4. 上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            log.info("文件上传成功: {} -> {}/{}", originalName, bucket, objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new BusinessException("文件上传失败，请稍后重试");
        }

        // 5. 拼接访问 URL
        String url = endpoint + "/" + bucket + "/" + objectName;

        return FileUploadVO.builder()
                .url(url)
                .originalName(originalName)
                .size(file.getSize())
                .build();
    }

    /**
     * 从文件名中提取扩展名（不含点号）。
     * 例如 "avatar.jpg" → "jpg"；无扩展名返回 null。
     */
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null;
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
