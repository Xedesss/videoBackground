package com.videoBackground.health.dto.distributor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 分销商创建请求DTO
 */
@Data
public class DistributorCreateRequest {
    
    /**
     * 账号名称
     */
    @NotBlank(message = "账号名称不能为空")
    private String name;
    
    /**
     * 账号类型（general/distributor）
     */
    @NotBlank(message = "账号类型不能为空")
    private String type;
    
    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    private String contact;
    
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    
    /**
     * 父级分销商ID
     */
    private Long parentId;
    
    /**
     * 账号状态，默认active
     */
    private String status = "active";
    
    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;
} 