package com.linqiumeng.mediavault.controller;

import com.linqiumeng.mediavault.dto.ApiResponse;
import com.linqiumeng.mediavault.entity.FileEntity;
import com.linqiumeng.mediavault.repository.FileRepository;
import com.linqiumeng.mediavault.utils.AliOssUtil;
import com.linqiumeng.mediavault.utils.MessageConstant;
import com.linqiumeng.mediavault.utils.file.OssMultipart;
import com.linqiumeng.mediavault.utils.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 文件上传
     *
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

            try {


                // 截取原始文件名的后缀
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                if (extension.isEmpty()) {
                    throw new IllegalArgumentException(MessageConstant.INVALID_FILE_EXTENSION);
                }

                // 构造新文件名称
                String objectName = UUID.randomUUID() + extension;

                // 文件的请求路径
                Map<String, Object> uploadResult = aliOssUtil.upload(file.getBytes(), objectName);
                String dataurl = (String) uploadResult.get("fileUrl");
                log.info("文件上传成功，路径：{}", dataurl);

                // 保存文件信息到数据库
                FileEntity fileEntity = new FileEntity();
                // 文件的原始名称
                fileEntity.setFileName(originalFilename);
                //OSS 中的唯一标识符
                fileEntity.setFileKey(objectName);
                // 保存文件的临时存储路径
                fileEntity.setFilePath("/");
                // 文件的大小
                fileEntity.setFileSize(file.getSize());
                // 文件的媒体类型
                fileEntity.setContentType(file.getContentType());


                // 获取当前登录用户的 ID
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                log.info("当前登录用户：{}", authentication);
                if (authentication != null && authentication.isAuthenticated()) {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();
                    log.info("当前登录用户名：{}", username);
                    Long userId = jwtTokenProvider.getUserIdFromUsername(username);
                    log.info("当前登录用户ID：{}", userId);
                    fileEntity.setUserId(Math.toIntExact(userId));
                } else {
                    throw new IllegalStateException("User is not authenticated");
                }
                // 文件的状态
                fileEntity.setStatus("uploaded");
                // 设置其他字段
                fileEntity.setDescription("文件描述"); // 根据实际需求设置描述
                fileEntity.setTags("tag1,tag2"); // 根据实际需求设置标签
                fileEntity.setDownloadCount(0); // 初始下载次数为0
                fileEntity.setPermissions("public"); // 初始权限为公共
                // 添加 dataurl 字段
                fileEntity.setDataurl(dataurl);
                // 设置上传时间
                fileEntity.setUploadTime(Instant.from(LocalDateTime.now().atZone(ZoneId.systemDefault())));
                // 设置更新时间
                fileEntity.setUpdateTime(Instant.from(LocalDateTime.now().atZone(ZoneId.systemDefault())));


                // 保存文件信息到数据库
                fileRepository.save(fileEntity);
                fileUrls.add(dataurl);

            } catch (IOException e) {
                log.error("文件传输失败: {}", e.getMessage(), e);
                throw e;
            }
        }
        // 成功
        return ResponseEntity.ok(ApiResponse.success(fileUrls));
    }

    /**
     * 根据 token 获取用户的文件信息
     *
     * @param token 用户的 token
     * @return 用户的文件信息列表
     */
    @GetMapping("/file-info")
    @ApiOperation("根据 token 获取用户的文件信息")
    public ResponseEntity<ApiResponse<?>> getUserFiles(@RequestHeader("Authorization") String authorization) {
        log.info("获取用户文件信息请求，token: {}", authorization);

        // 去除 Bearer 前缀
        String token = authorization.replace("Bearer ", "").trim();


        // 解析 token 获取 user_id
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("无效的 token"));
        }

        log.info("当前用户 ID: {}", userId);

        // 查询用户的所有文件信息
        List<FileEntity> files = fileRepository.findByUserId(Math.toIntExact(userId));
        if (files.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("没有找到文件信息"));
        }

        // 将文件信息转换为 DTO 或者直接返回
        List<FileEntity> fileDtos = files.stream()
                .map(file -> {
                    FileEntity fileDto = new FileEntity();
                    fileDto.setId(file.getId());
                    fileDto.setFileName(file.getFileName());
                    fileDto.setFileKey(file.getFileKey());
                    fileDto.setFilePath(file.getFilePath());
                    fileDto.setFileSize(file.getFileSize());
                    fileDto.setContentType(file.getContentType());
                    fileDto.setUserId(file.getUserId());
                    fileDto.setStatus(file.getStatus());
                    fileDto.setDescription(file.getDescription());
                    fileDto.setTags(file.getTags());
                    fileDto.setDownloadCount(file.getDownloadCount());
                    fileDto.setPermissions(file.getPermissions());
                    fileDto.setDataurl(file.getDataurl());
                    fileDto.setUploadTime(file.getUploadTime());
                    fileDto.setUpdateTime(file.getUpdateTime());
                    return fileDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(fileDtos));
    }
}
