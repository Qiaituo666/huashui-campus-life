package com.huashui.user.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 文件上传配置。
 * <p>
 * 替换默认的 SpringEncoder 为 SpringFormEncoder，
 * 使本模块的所有 Feign 客户端都能以 multipart/form-data 格式发送文件。
 * {@code SpringFormEncoder} 兼容普通请求，不影响非文件上传的 Feign 调用。
 * </p>
 *
 * @author freedom0213
 */
@Configuration
public class FeignMultipartConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignMultipartConfig(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
