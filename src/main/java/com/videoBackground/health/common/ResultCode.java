package com.videoBackground.health.common;

/**
 * 结果状态码枚举
 */
public enum ResultCode implements IErrorCode {
    // 成功
    SUCCESS(0, "success"),
    
    // 参数错误
    VALIDATE_FAILED(1001, "参数错误"),
    
    // 未登录或登录已过期
    UNAUTHORIZED(1002, "未登录或登录已过期"),
    
    // 视频不存在或已下架
    VIDEO_NOT_EXIST(2001, "视频不存在或已下架"),
    
    // 无观看权限
    VIDEO_NO_PERMISSION(2002, "无观看权限"),
    
    // 积分不足
    POINTS_NOT_ENOUGH(3001, "积分不足"),
    
    // 兑换失败，库存不足
    EXCHANGE_STOCK_EMPTY(3002, "兑换失败，库存不足"),
    
    // 服务器内部错误
    FAILED(5000, "服务器内部错误");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
} 