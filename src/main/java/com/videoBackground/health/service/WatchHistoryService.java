package com.videoBackground.health.service;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.vo.WatchHistoryVO;

/**
 * 观看记录服务接口
 */
public interface WatchHistoryService {
    
    /**
     * 获取用户观看记录
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 观看记录分页结果
     */
    PageResult<WatchHistoryVO> getUserWatchHistory(Long userId, Integer page, Integer pageSize);
} 