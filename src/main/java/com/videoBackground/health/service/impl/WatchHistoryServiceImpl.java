package com.videoBackground.health.service.impl;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.mapper.VideoMapper;
import com.videoBackground.health.mapper.WatchHistoryMapper;
import com.videoBackground.health.service.WatchHistoryService;
import com.videoBackground.health.vo.WatchHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 观看历史服务实现
 */
@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {
    
    @Autowired
    private WatchHistoryMapper watchHistoryMapper;
    
    @Autowired
    private VideoMapper videoMapper;
    
    @Override
    public PageResult<WatchHistoryVO> getUserWatchHistory(Long userId, Integer page, Integer pageSize) {
        // 计算偏移量
        Integer offset = (page - 1) * pageSize;
        
        // 查询用户的观看历史记录
        List<WatchHistoryVO> historyList = watchHistoryMapper.selectUserWatchHistory(userId, offset, pageSize);
        
        // 查询总记录数
        Long total = watchHistoryMapper.countUserWatchHistory(userId);
        
        // 返回分页结果
        return new PageResult<>(total, historyList);
    }
    
    @Override
    @Transactional
    public double reportVideoProgress(Long videoId, Long userId, Integer watchTime, Integer totalTime) {
        // 计算观看进度
        double progress = Math.min(1.0, (double) watchTime / totalTime);
        
        // 更新观看记录
        int updated = watchHistoryMapper.updateWatchHistory(videoId, userId, watchTime, totalTime, progress, new Date());
        
        // 如果没有更新记录，则创建新记录
        if (updated == 0) {
            watchHistoryMapper.insertWatchHistory(videoId, userId, watchTime, totalTime, progress, new Date());
        }
        
        // 增加视频观看次数
        videoMapper.incrementViewCount(videoId);
        
        return progress;
    }
} 