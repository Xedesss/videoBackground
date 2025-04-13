package com.videoBackground.health.controller.admin;

import com.videoBackground.health.common.ApiResponse;
import com.videoBackground.health.common.ApiStatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 通用接口控制器
 */
@RestController
@RequestMapping("/api")
public class CommonController {
    
    // 模拟存储路径
    private static final String UPLOAD_DIR = "uploads";
    
    @Value("${spring.servlet.multipart.max-file-size:10MB}")
    private String maxFileSize;
    
    /**
     * 通用文件上传接口
     *
     * @param file 文件数据
     * @param type 文件类型
     * @return 上传结果
     */
    @PostMapping("/upload")
    public ApiResponse<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        
        if (file.isEmpty()) {
            return ApiResponse.error(ApiStatusCode.BAD_REQUEST, "文件不能为空");
        }
        
        try {
            // 根据类型确定存储目录
            String targetDir;
            switch (type) {
                case "image":
                    targetDir = UPLOAD_DIR + "/images";
                    break;
                case "video":
                    targetDir = UPLOAD_DIR + "/videos";
                    break;
                case "document":
                    targetDir = UPLOAD_DIR + "/documents";
                    break;
                default:
                    return ApiResponse.error(ApiStatusCode.BAD_REQUEST, "不支持的文件类型");
            }
            
            // 确保目录存在
            Files.createDirectories(Paths.get(targetDir));
            
            // 生成文件名（使用UUID避免文件名冲突）
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileExtension;
            
            // 保存文件
            Path filePath = Paths.get(targetDir, fileName);
            file.transferTo(filePath.toFile());
            
            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("url", "/api/files/" + type + "s/" + fileName);
            result.put("filename", fileName);
            result.put("size", file.getSize());
            result.put("mimeType", file.getContentType());
            
            return ApiResponse.success("上传成功", result);
            
        } catch (IOException e) {
            return ApiResponse.error(ApiStatusCode.INTERNAL_SERVER_ERROR, "文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取系统配置
     *
     * @return 系统配置
     */
    @GetMapping("/config")
    public ApiResponse<Map<String, Object>> getSystemConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // 上传限制
        Map<String, Object> uploadLimits = new HashMap<>();
        uploadLimits.put("maxFileSize", parseFileSize(maxFileSize));
        uploadLimits.put("allowedFormats", Arrays.asList("mp4", "mov", "avi", "wmv"));
        config.put("uploadLimits", uploadLimits);
        
        // 系统信息
        config.put("systemName", "健康管理系统");
        config.put("version", "1.0.0");
        config.put("contactEmail", "support@example.com");
        
        return ApiResponse.success(config);
    }
    
    /**
     * 解析文件大小字符串为字节数
     */
    private long parseFileSize(String sizeStr) {
        sizeStr = sizeStr.trim().toUpperCase();
        long multiplier;
        
        if (sizeStr.endsWith("KB")) {
            multiplier = 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        } else if (sizeStr.endsWith("MB")) {
            multiplier = 1024 * 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        } else if (sizeStr.endsWith("GB")) {
            multiplier = 1024 * 1024 * 1024;
            sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
        } else {
            multiplier = 1;
        }
        
        try {
            return (long) (Double.parseDouble(sizeStr) * multiplier);
        } catch (NumberFormatException e) {
            return 10 * 1024 * 1024; // 默认10MB
        }
    }
} 