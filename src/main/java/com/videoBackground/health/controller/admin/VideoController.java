package com.videoBackground.health.controller.admin;

import com.videoBackground.health.common.ApiResponse;
import com.videoBackground.health.common.ApiStatusCode;
import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.video.VideoDTO;
import com.videoBackground.health.dto.video.VideoDetailDTO;
import com.videoBackground.health.dto.video.VideoListRequest;
import com.videoBackground.health.service.admin.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频管理控制器
 */
@RestController
@RequestMapping("/api/videos")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    /**
     * 获取视频列表
     *
     * @param request 请求参数
     * @return 视频列表
     */
    @GetMapping
    public ApiResponse<PageResult<VideoDTO>> getVideoList(VideoListRequest request) {
        PageResult<VideoDTO> result = videoService.getVideoList(request);
        return ApiResponse.success(result);
    }
    
    /**
     * 上传视频
     *
     * @param title 视频标题
     * @param description 视频描述
     * @param status 视频状态
     * @param file 视频文件
     * @param cover 封面图
     * @param tags 标签
     * @return 上传结果
     */
    @PostMapping("/upload")
    public ApiResponse<Map<String, Object>> uploadVideo(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false, defaultValue = "draft") String status,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "cover", required = false) MultipartFile cover,
            @RequestParam(value = "tags", required = false) List<String> tags) {
        
        Map<String, Object> uploadResult = videoService.uploadVideo(title, description, status, file, cover, tags);
        return ApiResponse.success("上传成功", uploadResult);
    }
    
    /**
     * 获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    @GetMapping("/{id}")
    public ApiResponse<VideoDetailDTO> getVideoDetail(@PathVariable("id") Long id) {
        VideoDetailDTO detail = videoService.getVideoDetail(id);
        if (detail == null) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "视频不存在");
        }
        return ApiResponse.success(detail);
    }
    
    /**
     * 更新视频信息
     *
     * @param id 视频ID
     * @param title 视频标题
     * @param description 视频描述
     * @param cover 封面图
     * @param tags 标签
     * @param status 视频状态
     * @param isRecommended 是否推荐
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ApiResponse<Map<String, Object>> updateVideo(
            @PathVariable("id") Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "cover", required = false) MultipartFile cover,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "isRecommended", required = false) Boolean isRecommended) {
        
        boolean success = videoService.updateVideo(id, title, description, cover, tags, status, isRecommended);
        
        if (!success) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "视频不存在");
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("status", status);
        
        return ApiResponse.success("更新成功", result);
    }
    
    /**
     * 删除视频
     *
     * @param id 视频ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVideo(@PathVariable("id") Long id) {
        boolean success = videoService.deleteVideo(id);
        
        if (!success) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "视频不存在");
        }
        
        return ApiResponse.success("删除成功", null);
    }
    
    /**
     * 视频分配权限
     *
     * @param id 视频ID
     * @param distributorIds 分销商ID列表
     * @return 权限设置结果
     */
    @PostMapping("/{id}/permissions")
    public ApiResponse<Map<String, Object>> setVideoPermissions(
            @PathVariable("id") Long id,
            @RequestBody List<Long> distributorIds) {
        
        Map<String, Object> result = videoService.setVideoPermissions(id, distributorIds);
        
        if (result == null) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "视频不存在");
        }
        
        return ApiResponse.success("权限设置成功", result);
    }
} 