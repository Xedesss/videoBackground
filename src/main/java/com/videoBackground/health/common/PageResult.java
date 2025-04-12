package com.videoBackground.health.common;

import lombok.Data;

import java.util.List;

/**
 * 分页结果包装类
 */
@Data
public class PageResult<T> {
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 总页数
     */
    private Integer pages;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 构造方法
     */
    public PageResult(Integer page, Integer pageSize, Long total, List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }
} 