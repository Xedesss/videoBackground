package com.videoBackground.health.vo;

import lombok.Data;

import java.util.Date;

/**
 * 观看历史视图对象
 */
@Data
public class WatchHistoryVO {
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 视频标题
     */
    private String videoTitle;
    
    /**
     * 视频封面
     */
    private String videoCover;
    
    /**
     * 视频分类
     */
    private String categoryName;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 观看时间（秒）
     */
    private Integer watchTime;
    
    /**
     * 视频总时长（秒）
     */
    private Integer totalTime;
    
    /**
     * 观看进度（百分比）
     */
    private Double progress;
    
    /**
     * 最后观看时间
     */
    private Date lastWatchTime;
} 