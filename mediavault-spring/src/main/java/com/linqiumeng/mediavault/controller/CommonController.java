package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.utils.AliOssUtil;
import com.linqiumeng.mediavault.utils.MessageConstant;
import com.linqiumeng.mediavault.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                log.error("文件名为空");
                return Result.error(MessageConstant.INVALID_FILE_NAME);
            }

            // 截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (extension.isEmpty()) {
                log.error("文件扩展名为空");
                return Result.error(MessageConstant.INVALID_FILE_EXTENSION);
            }

            // 构造新文件名称
            String objectName = UUID.randomUUID() + extension;

            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("文件上传成功，路径：{}", filePath);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage(), e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }
}
