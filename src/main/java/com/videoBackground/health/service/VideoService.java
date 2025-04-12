package com.videoBackground.health.service;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.vo.VideoDetailVO;
import com.videoBackground.health.vo.VideoListVO;

/**
 * 视频服务接口
 */
public interface VideoService {
    
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
    PageResult<VideoListVO> getVideoList(Integer page, Integer pageSize, String keyword, 
                                         String status, String courseType, 
                                         String sortField, String sortOrder);
    
    /**
     * 获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    VideoDetailVO getVideoDetail(Long id);
    
    /**
     * 上报视频观看进度
     *
     * @param videoId 视频ID
     * @param watchTime 观看时间(秒)
     * @param totalTime 总时长(秒)
     * @return 进度百分比
     */
    double reportVideoProgress(Long videoId, Long userId, Integer watchTime, Integer totalTime);
} 