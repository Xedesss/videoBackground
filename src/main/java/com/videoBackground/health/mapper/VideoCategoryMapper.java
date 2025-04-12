package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.VideoCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频分类Mapper接口
 */
@Mapper
public interface VideoCategoryMapper {
    
    /**
     * 获取所有视频分类
     * 
     * @return 分类列表
     */
    List<VideoCategory> getAllCategories();
    
    /**
     * 根据ID获取视频分类
     * 
     * @param id 分类ID
     * @return 分类信息
     */
    VideoCategory getCategoryById(@Param("id") Long id);
} 