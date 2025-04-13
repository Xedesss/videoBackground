package com.videoBackground.health.dto.video;

import lombok.Data;

/**
 * 视频列表请求DTO
 */
@Data
public class VideoListRequest {
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 10;
    
    /**
     * 视频标题搜索
     */
    private String title;
    
    /**
     * 视频状态筛选
     */
    private String status;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方式
     */
    private String orderType = "desc";
} 