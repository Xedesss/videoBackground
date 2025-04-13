package com.videoBackground.health.service.admin.impl;

import com.videoBackground.health.common.ApiStatusCode;
import com.videoBackground.health.dto.admin.LoginResponse;
import com.videoBackground.health.entity.Admin;
import com.videoBackground.health.mapper.AdminMapper;
import com.videoBackground.health.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 管理员服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    // 模拟JWT Token的颁发，实际项目中应该使用JWT库
    private static final Map<String, String> tokenStorage = new HashMap<>();
    
    @Override
    public LoginResponse login(String username, String password) {
        Admin admin = getAdminByUsername(username);
        
        if (admin == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 实际项目中应进行密码加密比对
        if (!password.equals(admin.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 生成Token
        String token = UUID.randomUUID().toString();
        tokenStorage.put(token, username);
        
        // 返回登录信息
        LoginResponse response = new LoginResponse();
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());
        response.setRole(admin.getRole());
        response.setToken(token);
        
        // 获取权限（此处为模拟数据，实际应该从数据库获取）
        List<String> permissions = new ArrayList<>();
        if ("admin".equals(admin.getRole())) {
            permissions.add("video.upload");
            permissions.add("video.edit");
            permissions.add("distributor.manage");
        } else {
            permissions.add("video.view");
        }
        response.setPermissions(permissions);
        
        return response;
    }
    
    @Override
    public void logout() {
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        // 从Header中获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // 移除token
            tokenStorage.remove(token);
        }
    }
    
    @Override
    public Map<String, Object> getCurrentUserInfo() {
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        // 从Header中获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            
            // 根据token获取用户名
            String username = tokenStorage.get(token);
            if (username != null) {
                Admin admin = getAdminByUsername(username);
                
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", admin.getId());
                userInfo.put("username", admin.getUsername());
                userInfo.put("role", admin.getRole());
                userInfo.put("avatar", admin.getAvatar());
                
                // 获取权限（此处为模拟数据，实际应该从数据库获取）
                List<String> permissions = new ArrayList<>();
                if ("admin".equals(admin.getRole())) {
                    permissions.add("video.upload");
                    permissions.add("video.edit");
                    permissions.add("distributor.manage");
                } else {
                    permissions.add("video.view");
                }
                userInfo.put("permissions", permissions);
                
                return userInfo;
            }
        }
        
        throw new RuntimeException("未登录或登录已过期");
    }
    
    @Override
    public Admin getAdminByUsername(String username) {
        // 实际项目中应该从数据库查询，这里使用模拟数据
        if ("admin".equals(username)) {
            Admin admin = new Admin();
            admin.setId(1L);
            admin.setUsername("admin");
            admin.setPassword("123456"); // 实际项目中应该是加密后的密码
            admin.setRole("admin");
            admin.setAvatar("https://example.com/avatar.jpg");
            admin.setCreateTime(new Date());
            admin.setLastLoginTime(new Date());
            admin.setStatus(1);
            return admin;
        }
        return null;
    }
} 