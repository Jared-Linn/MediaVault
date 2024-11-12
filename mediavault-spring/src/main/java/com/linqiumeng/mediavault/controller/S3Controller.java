package com.linqiumeng.mediavault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;

@RestController
//@RequestMapping("/api")
public class S3Controller {

    @Autowired
    private S3Client s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/generate-signed-url")
    public ResponseEntity<String> generateSignedUrl(@RequestParam String key) {
        try {
            // 创建 PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // 创建 S3 Presigner
            S3Presigner presigner = S3Presigner.builder().build();

            // 生成预签名 URL
            PresignedPutObjectRequest presignedPutObjectRequest = presigner.presignPutObject(builder -> builder
                    .signatureDuration(Duration.ofSeconds(3600))
                    .putObjectRequest(putObjectRequest));

            // 返回预签名 URL
            return ResponseEntity.ok(presignedPutObjectRequest.url().toString());
        } catch (Exception e) {
            // 错误处理
            return ResponseEntity.status(500).body("生成上传 URL 失败: " + e.getMessage());
        }
    }
}
