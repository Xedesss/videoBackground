package com.videoBackground.health.controller.admin;

import com.videoBackground.health.common.ApiResponse;
import com.videoBackground.health.dto.admin.LoginRequest;
import com.videoBackground.health.dto.admin.LoginResponse;
import com.videoBackground.health.service.admin.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = adminService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ApiResponse.success("登录成功", loginResponse);
    }

    /**
     * 登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        adminService.logout();
        return ApiResponse.success("登出成功", null);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/userinfo")
    public ApiResponse<Map<String, Object>> getUserInfo() {
        Map<String, Object> userInfo = adminService.getCurrentUserInfo();
        return ApiResponse.success(userInfo);
    }
} 