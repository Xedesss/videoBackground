package com.videoBackground.health.dto.distributor;

import lombok.Data;

/**
 * 分销商更新请求DTO
 */
@Data
public class DistributorUpdateRequest {
    
    /**
     * 账号名称
     */
    private String name;
    
    /**
     * 联系人
     */
    private String contact;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 账号状态
     */
    private String status;
    
    /**
     * 登录密码
     */
    private String password;
} 