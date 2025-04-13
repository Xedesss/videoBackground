package com.videoBackground.health.dto.video;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频详情DTO
 */
@Data
public class VideoDetailDTO {
    
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
     * 时长（秒）
     */
    private Integer duration;
    
    /**
     * 文件大小（字节）
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
     * 状态
     */
    private String status;
    
    /**
     * 是否推荐
     */
    private Boolean isRecommended;
    
    /**
     * 标签
     */
    private List<String> tags;
    
    /**
     * 分销商信息
     */
    private List<Map<String, Object>> distributors;
} 