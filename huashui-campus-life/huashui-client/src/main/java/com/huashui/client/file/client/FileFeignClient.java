package com.huashui.client.file.client;

import com.huashui.common.domain.vo.FileUploadVO;
import com.huashui.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 Feign 客户端。
 * <p>
 * 其他业务模块通过此接口调用 huashui-file 的上传功能，
 * 无需直接依赖 file 模块。
 * </p>
 *
 * @author 陈会闯
 */
@FeignClient(name = "huashui-file", path = "/file")
public interface FileFeignClient {

    /**
     * 上传文件到 OSS。
     * <p>
     * Feign 发送 multipart/form-data 请求，file 服务返回文件 URL。
     * </p>
     *
     * @param file 要上传的文件
     * @return 文件访问 URL + 元信息
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<FileUploadVO> upload(@RequestPart("file") MultipartFile file);
}
