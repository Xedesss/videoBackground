package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.WatchHistory;
import com.videoBackground.health.vo.WatchHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 观看历史Mapper接口
 */
@Mapper
public interface WatchHistoryMapper {
    
    /**
     * 查询用户观看历史
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 观看历史列表
     */
    List<WatchHistoryVO> selectUserWatchHistory(
            @Param("userId") Long userId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);
    
    /**
     * 统计用户观看历史总数
     *
     * @param userId 用户ID
     * @return 总数
     */
    Long countUserWatchHistory(@Param("userId") Long userId);
    
    /**
     * 更新观看历史
     *
     * @param videoId 视频ID
     * @param userId 用户ID
     * @param watchTime 观看时间（秒）
     * @param totalTime 视频总时长（秒）
     * @param progress 观看进度
     * @param lastWatchTime 最后观看时间
     * @return 影响行数
     */
    int updateWatchHistory(
            @Param("videoId") Long videoId,
            @Param("userId") Long userId,
            @Param("watchTime") Integer watchTime,
            @Param("totalTime") Integer totalTime,
            @Param("progress") double progress,
            @Param("lastWatchTime") Date lastWatchTime);
    
    /**
     * 插入观看历史
     *
     * @param videoId 视频ID
     * @param userId 用户ID
     * @param watchTime 观看时间（秒）
     * @param totalTime 视频总时长（秒）
     * @param progress 观看进度
     * @param lastWatchTime 最后观看时间
     * @return 影响行数
     */
    int insertWatchHistory(
            @Param("videoId") Long videoId,
            @Param("userId") Long userId,
            @Param("watchTime") Integer watchTime,
            @Param("totalTime") Integer totalTime,
            @Param("progress") double progress,
            @Param("lastWatchTime") Date lastWatchTime);
} 