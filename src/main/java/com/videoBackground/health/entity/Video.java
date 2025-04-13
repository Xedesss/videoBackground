package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 视频实体
 */
@Data
public class Video {
    
    /**
     * 视频ID
     */
    private Long id;
    
    /**
     * 视频标题
     */
    private String title;
    
    /**
     * 视频描述
     */
    private String description;
    
    /**
     * 视频URL
     */
    private String url;
    
    /**
     * 封面URL
     */
    private String cover;
    
    /**
     * 视频时长（秒）
     */
    private Integer duration;
    
    /**
     * 视频文件大小（字节）
     */
    private Long size;
    
    /**
     * 上传时间
     */
    private Date uploadTime;
    
    /**
     * 观看次数
     */
    private Integer viewCount;
    
    /**
     * 点赞次数
     */
    private Integer likeCount;
    
    /**
     * 评论次数
     */
    private Integer commentCount;
    
    /**
     * 视频状态（published/draft/disabled）
     */
    private String status;
    
    /**
     * 是否推荐
     */
    private Boolean isRecommended;
    
    /**
     * 标签（非数据库字段）
     */
    private transient List<String> tags;
    
    /**
     * 分销商ID（非数据库字段）
     */
    private transient List<Long> distributorIds;
} 