package com.linqiumeng.mediavault.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类
 * 封装文件上传到OSS的逻辑。
 */
@Slf4j
@Component
public class AliOssUtil {

    @Value("${ali.oss.endpoint}")
    private String endpoint;

    @Value("${ali.oss.access-key-id}")
    private String accessKeyId;

    @Value("${ali.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${ali.oss.bucket-name}")
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public Map<String, Object> upload(byte[] bytes, String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.", oe);
            log.error("Error Message: {}", oe.getErrorMessage());
            log.error("Error Code: {}", oe.getErrorCode());
            log.error("Request ID: {}", oe.getRequestId());
            log.error("Host ID: {}", oe.getHostId());
            throw new RuntimeException("文件上传失败", oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS, such as not being able to access the network.", ce);
            log.error("Error Message: {}", ce.getMessage());
            throw new RuntimeException("文件上传失败", ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 文件访问路径规则
        String fileUrl = String.format("https://%s.%s/%s", bucketName, endpoint, objectName);

        log.info("文件上传到: {}", fileUrl);

        Map<String, Object> result = new HashMap<>();
        result.put("fileUrl", fileUrl);
        result.put("objectName", objectName);
        result.put("bucketName", bucketName);
        result.put("endpoint", endpoint);

        return result;
    }
}
