package com.videoBackground.health.dto.distributor;

import lombok.Data;

/**
 * 分销商列表请求DTO
 */
@Data
public class DistributorListRequest {
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 10;
    
    /**
     * 账号名称搜索
     */
    private String name;
    
    /**
     * 账号类型
     */
    private String type;
    
    /**
     * 账号状态
     */
    private String status;
} 