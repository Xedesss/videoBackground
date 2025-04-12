package com.videoBackground.health.vo;

import lombok.Data;

/**
 * 视频观看历史视图对象
 */
@Data
public class WatchHistoryVO {
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 视频标题
     */
    private String title;
    
    /**
     * 封面图片URL
     */
    private String coverUrl;
    
    /**
     * 课程名称
     */
    private String courseName;
    
    /**
     * 观看时间（如：1天前观看）
     */
    private String watchTime;
    
    /**
     * 观看进度（秒）
     */
    private Integer progress;
    
    /**
     * 总时长（格式化为 MM:SS）
     */
    private String duration;
    
    /**
     * 是否已完成观看（进度超过95%）
     */
    private Boolean completed;
} 