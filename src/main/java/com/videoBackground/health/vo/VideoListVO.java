package com.videoBackground.health.vo;

import lombok.Data;

/**
 * 视频列表视图对象
 */
@Data
public class VideoListVO {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 课程名称
     */
    private String courseName;
    
    /**
     * 视频标题
     */
    private String title;
    
    /**
     * 状态：未开始、进行中、已结束
     */
    private String status;
    
    /**
     * 封面图URL
     */
    private String coverUrl;
    
    /**
     * 开课日期
     */
    private String startDate;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 倒计时
     */
    private String countdown;
    
    /**
     * 课程ID或期数
     */
    private String entryId;
    
    /**
     * 观看次数
     */
    private Integer viewCount;
    
    /**
     * 视频时长
     */
    private String duration;
    
    /**
     * 创建时间
     */
    private String createTime;
} 