package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;

/**
 * 视频实体类
 */
@Data
public class Video {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 讲师ID
     */
    private Long teacherId;
    
    /**
     * 课程名称
     */
    private String courseName;
    
    /**
     * 视频标题
     */
    private String title;
    
    /**
     * 封面图URL
     */
    private String coverUrl;
    
    /**
     * 视频URL
     */
    private String videoUrl;
    
    /**
     * 状态：未开始、进行中、已结束
     */
    private String status;
    
    /**
     * 开始时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 课程ID或期数
     */
    private String entryId;
    
    /**
     * 观看次数
     */
    private Integer viewCount;
    
    /**
     * 视频时长(秒)
     */
    private Integer duration;
    
    /**
     * 视频描述
     */
    private String description;
    
    /**
     * 是否有问题：0-否，1-是
     */
    private Boolean hasQuestions;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 