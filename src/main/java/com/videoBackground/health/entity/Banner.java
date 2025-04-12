package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;

/**
 * Banner轮播图实体类
 */
@Data
public class Banner {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 描述
     */
    private String desc;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 点击类型：video-视频详情，webview-网页
     */
    private String type;
    
    /**
     * 跳转链接
     */
    private String url;
    
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