package com.videoBackground.health.vo;

import lombok.Data;

/**
 * Banner轮播图视图对象
 */
@Data
public class BannerVO {
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
     * 排序
     */
    private Integer sort;
} 