package com.videoBackground.health.service.impl;

import com.videoBackground.health.entity.VideoCategory;
import com.videoBackground.health.mapper.VideoCategoryMapper;
import com.videoBackground.health.service.VideoCategoryService;
import com.videoBackground.health.vo.VideoCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频分类服务实现类
 */
@Service
public class VideoCategoryServiceImpl implements VideoCategoryService {
    
    @Autowired
    private VideoCategoryMapper videoCategoryMapper;
    
    /**
     * 获取所有视频分类
     * 
     * @return 分类列表
     */
    @Override
    public List<VideoCategoryVO> getAllCategories() {
        List<VideoCategory> categories = videoCategoryMapper.getAllCategories();
        return categories.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    /**
     * 根据ID获取视频分类
     * 
     * @param id 分类ID
     * @return 分类信息
     */
    @Override
    public VideoCategoryVO getCategoryById(Long id) {
        VideoCategory category = videoCategoryMapper.getCategoryById(id);
        return category != null ? convertToVO(category) : null;
    }
    
    /**
     * 将实体转换为视图对象
     */
    private VideoCategoryVO convertToVO(VideoCategory entity) {
        if (entity == null) {
            return null;
        }
        VideoCategoryVO vo = new VideoCategoryVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
} 