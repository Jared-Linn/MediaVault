package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.entity.FileEntity;
import com.linqiumeng.mediavault.repository.FileRepository;
import com.linqiumeng.mediavault.utils.AliOssUtil;
import com.linqiumeng.mediavault.utils.MessageConstant;
import com.linqiumeng.mediavault.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private FileRepository fileRepository;

    /**
     * 文件上传
     * @param files
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public ResponseEntity<ApiResponse<?>> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
        log.info("文件上传：{}", files);

        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new IllegalArgumentException("文件名为空");
            }

            // 截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (extension.isEmpty()) {
                throw new IllegalArgumentException(MessageConstant.INVALID_FILE_EXTENSION);
            }

            // 构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            // 文件的请求路径
            Map<String, Object> uploadResult = aliOssUtil.upload(file.getBytes(), objectName);
            String filePath = (String) uploadResult.get("fileUrl");
            log.info("文件上传成功，路径：{}", filePath);

            // 保存文件信息到数据库
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(originalFilename);
            fileEntity.setFileKey(objectName);
            fileEntity.setFileSize(file.getSize());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setStatus("uploaded");
            fileEntity.setDataurl(filePath); // 添加 dataurl 字段
            fileEntity.setUserId(1); // 假设用户ID为1，实际应用中应从上下文中获取
            fileRepository.save(fileEntity);

            fileUrls.add(filePath);
        }
        // 成功
        return ResponseEntity.ok(ApiResponse.success(fileUrls));
    }
}
