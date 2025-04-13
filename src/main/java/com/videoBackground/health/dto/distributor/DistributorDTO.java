package com.videoBackground.health.dto.distributor;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 分销商DTO
 */
@Data
public class DistributorDTO {
    
    /**
     * 分销商ID
     */
    private Long id;
    
    /**
     * 账号名称
     */
    private String name;
    
    /**
     * 账号类型
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
     * 账号状态
     */
    private String status;
    
    /**
     * 渠道码
     */
    private String channelCode;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 子分销商
     */
    private List<DistributorDTO> children;
} 