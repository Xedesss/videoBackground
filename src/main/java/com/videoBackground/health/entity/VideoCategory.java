package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;

/**
 * 视频分类实体类
 */
@Data
public class VideoCategory {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类图标
     */
    private String icon;
    
    /**
     * 排序，数值越小越靠前
     */
    private Integer sort;
    
    /**
     * 状态：0-禁用，1-启用
     */
    private Boolean status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 