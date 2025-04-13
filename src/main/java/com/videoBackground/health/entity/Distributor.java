package com.videoBackground.health.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 分销商实体
 */
@Data
public class Distributor {
    
    /**
     * 分销商ID
     */
    private Long id;
    
    /**
     * 账号名称
     */
    private String name;
    
    /**
     * 账号类型（general/distributor）
     */
    private String type;
    
    /**
     * 联系人
     */
    private String contact;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 客户数量
     */
    private Integer customerCount;
    
    /**
     * 订单数量
     */
    private Integer orderCount;
    
    /**
     * 账号状态（active/disabled）
     */
    private String status;
    
    /**
     * 渠道码
     */
    private String channelCode;
    
    /**
     * 父级分销商ID
     */
    private Long parentId;
    
    /**
     * 登录密码（加密后）
     */
    private String password;
    
    /**
     * 子分销商（非数据库字段）
     */
    private transient List<Distributor> children;
    
    /**
     * 可访问的视频IDs（非数据库字段）
     */
    private transient List<Long> accessibleVideoIds;
} 