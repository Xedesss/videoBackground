package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 管理员实体
 */
@Data
public class Admin {
    /**
     * 管理员ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码（加密后）
     */
    private String password;
    
    /**
     * 角色
     */
    private String role;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    
    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;
    
    /**
     * 权限列表（非数据库字段）
     */
    private transient List<String> permissions;
} 