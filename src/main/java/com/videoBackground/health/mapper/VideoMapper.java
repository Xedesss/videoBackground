package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频Mapper接口
 */
@Mapper
public interface VideoMapper {
    
    /**
     * 获取视频列表
     *
     * @param keyword 搜索关键词
     * @param status 视频状态：未开始、进行中、已结束
     * @param courseType 课程类型
     * @param sortField 排序字段
     * @param sortOrder 排序方式
     * @return 视频列表
     */
    List<Video> getVideoList(@Param("keyword") String keyword, 
                             @Param("status") String status,
                             @Param("courseType") String courseType,
                             @Param("sortField") String sortField,
                             @Param("sortOrder") String sortOrder);
    
    /**
     * 根据ID获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    Video getVideoById(@Param("id") Long id);
    
    /**
     * 获取视频问题ID列表
     *
     * @param videoId 视频ID
     * @return 问题ID列表
     */
    List<Long> getVideoQuestionIds(@Param("videoId") Long videoId);
    
    /**
     * 获取相关视频
     *
     * @param categoryId 分类ID
     * @param videoId 当前视频ID
     * @param limit 限制数量
     * @return 相关视频列表
     */
    List<Video> getRelatedVideos(@Param("categoryId") Long categoryId, 
                                 @Param("videoId") Long videoId,
                                 @Param("limit") Integer limit);
    
    /**
     * 更新视频观看次数
     *
     * @param videoId 视频ID
     * @return 更新结果
     */
    int updateViewCount(@Param("videoId") Long videoId);
} 