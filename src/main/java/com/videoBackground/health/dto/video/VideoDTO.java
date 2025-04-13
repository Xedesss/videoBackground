package com.videoBackground.health.dto.video;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 视频DTO
 */
@Data
public class VideoDTO {
    
    /**
     * 视频ID
     */
    private Long id;
    
    /**
     * 视频标题
     */
    private String title;
    
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
     * 状态
     */
    private String status;
    
    /**
     * 是否推荐
     */
    private Boolean isRecommended;
} 