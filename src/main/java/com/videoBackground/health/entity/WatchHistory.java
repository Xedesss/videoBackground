package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;

/**
 * 观看记录实体类
 */
@Data
public class WatchHistory {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 视频ID
     */
    private Long videoId;
    
    /**
     * 观看进度(百分比)
     */
    private Integer progress;
    
    /**
     * 上次观看位置(秒)
     */
    private Integer lastPosition;
    
    /**
     * 观看时间
     */
    private Date watchTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 