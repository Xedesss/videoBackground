package com.videoBackground.health.vo;

import lombok.Data;

/**
 * 视频分类视图对象
 */
@Data
public class VideoCategoryVO {
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
     * 排序
     */
    private Integer sort;
} 