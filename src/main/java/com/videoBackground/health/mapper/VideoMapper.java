package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 视频Mapper接口
 */
@Mapper
public interface VideoMapper {
    
    /**
     * 分页查询视频列表
     *
     * @param offset 偏移量
     * @param limit 查询数量
     * @param title 标题搜索
     * @param status 状态筛选
     * @param orderBy 排序字段
     * @param orderType 排序方式
     * @return 视频列表
     */
    List<Video> selectVideoList(
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("title") String title,
            @Param("status") String status,
            @Param("orderBy") String orderBy,
            @Param("orderType") String orderType);
    
    /**
     * 查询符合条件的视频总数
     *
     * @param title 标题搜索
     * @param status 状态筛选
     * @return 总数
     */
    Long countVideoList(@Param("title") String title, @Param("status") String status);
    
    /**
     * 根据ID查询视频
     *
     * @param id 视频ID
     * @return 视频信息
     */
    @Select("SELECT * FROM video WHERE id = #{id}")
    Video selectVideoById(@Param("id") Long id);
    
    /**
     * 插入视频
     *
     * @param video 视频信息
     * @return 影响行数
     */
    int insertVideo(Video video);
    
    /**
     * 更新视频
     *
     * @param video 视频信息
     * @return 影响行数
     */
    int updateVideo(Video video);
    
    /**
     * 删除视频
     *
     * @param id 视频ID
     * @return 影响行数
     */
    int deleteVideo(@Param("id") Long id);
    
    /**
     * 更新视频观看次数
     *
     * @param id 视频ID
     * @return 影响行数
     */
    @Update("UPDATE video SET view_count = view_count + 1 WHERE id = #{id}")
    int incrementViewCount(@Param("id") Long id);
    
    /**
     * 查询视频标签
     *
     * @param videoId 视频ID
     * @return 标签列表
     */
    List<String> selectVideoTags(@Param("videoId") Long videoId);
    
    /**
     * 插入视频标签
     *
     * @param videoId 视频ID
     * @param tags 标签列表
     * @return 影响行数
     */
    int insertVideoTags(@Param("videoId") Long videoId, @Param("tags") List<String> tags);
    
    /**
     * 删除视频标签
     *
     * @param videoId 视频ID
     * @return 影响行数
     */
    int deleteVideoTags(@Param("videoId") Long videoId);
    
    /**
     * 查询视频分销商
     *
     * @param videoId 视频ID
     * @return 分销商列表
     */
    List<Map<String, Object>> selectVideoDistributors(@Param("videoId") Long videoId);
    
    /**
     * 插入视频分销商关系
     *
     * @param videoId 视频ID
     * @param distributorIds 分销商ID列表
     * @return 影响行数
     */
    int insertVideoDistributors(@Param("videoId") Long videoId, @Param("distributorIds") List<Long> distributorIds);
    
    /**
     * 删除视频分销商关系
     *
     * @param videoId 视频ID
     * @return 影响行数
     */
    int deleteVideoDistributors(@Param("videoId") Long videoId);
} 