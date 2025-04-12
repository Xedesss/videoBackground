package com.videoBackground.health.service;

import com.videoBackground.health.vo.VideoCategoryVO;

import java.util.List;

/**
 * 视频分类服务接口
 */
public interface VideoCategoryService {
    
    /**
     * 获取所有视频分类
     * 
     * @return 分类列表
     */
    List<VideoCategoryVO> getAllCategories();
    
    /**
     * 根据ID获取视频分类
     * 
     * @param id 分类ID
     * @return 分类信息
     */
    VideoCategoryVO getCategoryById(Long id);
} 