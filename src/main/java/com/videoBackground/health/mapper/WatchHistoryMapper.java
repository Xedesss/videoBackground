package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.WatchHistory;
import com.videoBackground.health.vo.WatchHistoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 观看记录Mapper接口
 */
public interface WatchHistoryMapper {
    
    /**
     * 获取用户观看记录列表
     *
     * @param userId 用户ID
     * @return 观看记录列表
     */
    List<WatchHistoryVO> getUserWatchHistory(@Param("userId") Long userId);
    
    /**
     * 获取用户对某视频的观看记录
     *
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 观看记录
     */
    WatchHistory getUserVideoWatchHistory(@Param("userId") Long userId, @Param("videoId") Long videoId);
    
    /**
     * 保存或更新观看记录
     *
     * @param userId 用户ID
     * @param videoId 视频ID
     * @param progress 进度百分比
     * @param lastPosition 上次观看位置(秒)
     * @return 影响行数
     */
    int saveWatchHistory(@Param("userId") Long userId, 
                         @Param("videoId") Long videoId,
                         @Param("progress") Integer progress, 
                         @Param("lastPosition") Integer lastPosition);
} 