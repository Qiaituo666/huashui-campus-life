package com.huashui.file.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO 对象存储配置。
 * <p>
 * 注册 MinioClient Bean，并在启动时自动检测/创建 Bucket。
 * 配置文件：bootstrap.yml 中的 minio.* 配置段。
 * </p>
 *
 * @author 陈会闯
 */
@Slf4j
@Configuration
public class MinioConfig {

    @Value("http://127.0.0.1:9000")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucket;

    /**
     * 初始化 MinioClient，注册为 Spring Bean 供全局注入使用。
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        log.info("MinIO 客户端初始化完成，endpoint: {}", endpoint);

        // 启动时自动检查并创建 bucket，避免运行时才发现缺失
        ensureBucketExists(client);

        return client;
    }

    /**
     * 确保目标 Bucket 存在，不存在则自动创建。
     */
    private void ensureBucketExists(MinioClient client) {
        try {
            boolean exists = client.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                client.makeBucket(
                        MakeBucketArgs.builder().bucket(bucket).build());
                log.info("MinIO Bucket [{}] 创建成功", bucket);
            } else {
                log.info("MinIO Bucket [{}] 已存在，跳过创建", bucket);
            }
        } catch (Exception e) {
            log.error("MinIO Bucket 初始化失败: {}", e.getMessage(), e);
        }
    }
}
