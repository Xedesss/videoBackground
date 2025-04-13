package com.videoBackground.health.service.admin;

import com.videoBackground.health.dto.admin.LoginResponse;
import com.videoBackground.health.entity.Admin;

import java.util.Map;

/**
 * 管理员服务接口
 */
public interface AdminService {
    
    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    LoginResponse login(String username, String password);
    
    /**
     * 管理员登出
     */
    void logout();
    
    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    Map<String, Object> getCurrentUserInfo();
    
    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return 管理员信息
     */
    Admin getAdminByUsername(String username);
} 