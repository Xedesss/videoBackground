package com.videoBackground.health.controller;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.common.Result;
import com.videoBackground.health.common.ResultCode;
import com.videoBackground.health.service.VideoCategoryService;
import com.videoBackground.health.service.VideoService;
import com.videoBackground.health.vo.VideoCategoryVO;
import com.videoBackground.health.vo.VideoDetailVO;
import com.videoBackground.health.vo.VideoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    
    @Autowired
    private VideoCategoryService videoCategoryService;
    
    @Autowired
    private VideoService videoService;
    
    /**
     * 测试用户ID（实际应该从用户会话中获取）
     */
    private static final Long TEST_USER_ID = 1L;
    
    /**
     * 获取视频分类列表
     *
     * @return 分类列表
     */
    @GetMapping("/categories")
    public Result<List<VideoCategoryVO>> getVideoCategories() {
        List<VideoCategoryVO> categories = videoCategoryService.getAllCategories();
        return Result.success(categories);
    }
    
    /**
     * 获取视频列表
     *
     * @param page 页码
     * @param pageSize 每页数量
     * @param keyword 搜索关键词
     * @param status 视频状态
     * @param courseType 课程类型
     * @param sortField 排序字段
     * @param sortOrder 排序方式
     * @return 分页结果
     */
    @GetMapping("/list")
    public Result<PageResult<VideoListVO>> getVideoList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "courseType", required = false) String courseType,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {
        
        PageResult<VideoListVO> result = videoService.getVideoList(
                page, pageSize, keyword, status, courseType, sortField, sortOrder);
        return Result.success(result);
    }
    
    /**
     * 获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    @GetMapping("/detail")
    public Result<VideoDetailVO> getVideoDetail(@RequestParam("id") Long id) {
        VideoDetailVO detail = videoService.getVideoDetail(id);
        if (detail == null) {
            return Result.failed(ResultCode.VIDEO_NOT_EXIST);
        }
        return Result.success(detail);
    }
    
    /**
     * 上报视频观看进度
     *
     * @param videoId 视频ID
     * @param watchTime 已观看时间（秒）
     * @param totalTime 视频总时长（秒）
     * @return 进度信息
     */
    @PostMapping("/progress")
    public Result<Map<String, Object>> reportVideoProgress(
            @RequestParam("videoId") Long videoId,
            @RequestParam("watchTime") Integer watchTime,
            @RequestParam("totalTime") Integer totalTime) {
        
        // 进行参数验证
        if (videoId == null || watchTime == null || totalTime == null) {
            return Result.validateFailed();
        }
        
        // 上报进度（在实际环境中，userId应该从用户会话中获取）
        double progress = videoService.reportVideoProgress(videoId, TEST_USER_ID, watchTime, totalTime);
        
        // 封装返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("videoId", videoId);
        resultMap.put("watchTime", watchTime);
        resultMap.put("totalTime", totalTime);
        resultMap.put("progress", progress);
        
        return Result.success(resultMap);
    }
} 