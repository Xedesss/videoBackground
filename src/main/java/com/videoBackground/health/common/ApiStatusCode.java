package com.videoBackground.health.common;

import lombok.Getter;

/**
 * API状态码枚举
 */
@Getter
public enum ApiStatusCode {
    
    // 成功
    SUCCESS(200, "success"),
    
    // 请求参数错误
    BAD_REQUEST(400, "请求参数错误"),
    
    // 未授权或token过期
    UNAUTHORIZED(401, "未授权或token过期"),
    
    // 权限不足
    FORBIDDEN(403, "权限不足"),
    
    // 资源不存在
    NOT_FOUND(404, "资源不存在"),
    
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");
    
    private final Integer code;
    private final String message;
    
    ApiStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
} 