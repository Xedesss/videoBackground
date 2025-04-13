package com.videoBackground.health.dto.admin;

import lombok.Data;

import java.util.List;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 角色
     */
    private String role;
    
    /**
     * JWT令牌
     */
    private String token;
    
    /**
     * 权限列表
     */
    private List<String> permissions;
} 