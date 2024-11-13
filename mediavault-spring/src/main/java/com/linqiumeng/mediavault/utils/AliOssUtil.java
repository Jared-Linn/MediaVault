package com.linqiumeng.mediavault.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;

@Component
@Slf4j
public class AliOssUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    private OSS ossClient;

    public AliOssUtil aliOssUtil() {
        return new AliOssUtil();
    }

    @PostConstruct
    public void init() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        log.info("OSS Client initialized: {}", ossClient);
    }

    public Map<String, Object> upload(byte[] content, String objectName) {
        Map<String, Object> result = new HashMap<>();

        try {
            PutObjectResult putResult = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
            result.put("fileUrl", fileUrl);
            result.put("objectName", objectName);
            result.put("etag", putResult.getETag());
        } catch (Exception e) {
            log.error("文件上传失败：{}", e.getMessage(), e);
            result.put("error", e.getMessage());
        }

        return result;
    }

    public void shutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
            log.info("OSS Client shutdown");
        }
    }
}
