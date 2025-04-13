package com.videoBackground.health.service;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.vo.WatchHistoryVO;

/**
 * 观看历史服务接口
 */
public interface WatchHistoryService {
    
    /**
     * 获取用户观看历史
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 观看历史分页结果
     */
    PageResult<WatchHistoryVO> getUserWatchHistory(Long userId, Integer page, Integer pageSize);
    
    /**
     * 添加或更新观看记录
     *
     * @param videoId 视频ID
     * @param userId 用户ID
     * @param watchTime 观看时间（秒）
     * @param totalTime 视频总时长（秒）
     * @return 观看进度（0-1之间的小数）
     */
    double reportVideoProgress(Long videoId, Long userId, Integer watchTime, Integer totalTime);
} 