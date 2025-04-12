package com.videoBackground.health.service;

import com.videoBackground.health.vo.BannerVO;

import java.util.List;

/**
 * Banner服务接口
 */
public interface BannerService {
    
    /**
     * 获取Banner列表
     *
     * @param type Banner类型
     * @param limit 数量限制
     * @return Banner列表
     */
    List<BannerVO> getBannerList(String type, Integer limit);
} 