package com.huashui.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.Data;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / SpringDoc API 文档配置
 * <p>
 * 读取各模块 YAML 中 hs.swagger.* 的自定义配置，
 * 创建 SpringDoc 所需的 GroupedOpenApi 和 OpenAPI Bean。
 * <p>
 * 仅在 hs.swagger.enable=true 且 classpath 上有 SpringDoc 时生效。
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hs.swagger")
@ConditionalOnClass(org.springdoc.core.models.GroupedOpenApi.class)
@ConditionalOnProperty(prefix = "hs.swagger", name = "enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {

    /** 是否启用 */
    private Boolean enable;

    /** 是否包装响应体（Knife4j 4.x 已弃用，保留兼容） */
    private Boolean enableResponseWrap;

    /** 接口扫描包路径，如 com.huashui.auth.controller */
    private String packagePath;

    /** 文档标题 */
    private String title;

    /** 文档描述 */
    private String description;

    /** 联系人/团队名 */
    private String contactName;

    /** 文档版本 */
    private String version;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan(packagePath)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact().name(contactName)));
    }
}
