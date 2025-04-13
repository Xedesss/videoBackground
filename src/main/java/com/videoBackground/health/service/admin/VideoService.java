package com.videoBackground.health.service.admin;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.video.VideoDTO;
import com.videoBackground.health.dto.video.VideoDetailDTO;
import com.videoBackground.health.dto.video.VideoListRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 视频服务接口
 */
public interface VideoService {
    
    /**
     * 获取视频列表
     *
     * @param request 请求参数
     * @return 视频列表分页结果
     */
    PageResult<VideoDTO> getVideoList(VideoListRequest request);
    
    /**
     * 上传视频
     *
     * @param title 视频标题
     * @param description 视频描述
     * @param status 视频状态
     * @param file 视频文件
     * @param cover 封面图
     * @param tags 标签
     * @return 上传结果
     */
    Map<String, Object> uploadVideo(String title, String description, String status,
                                   MultipartFile file, MultipartFile cover, List<String> tags);
    
    /**
     * 获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    VideoDetailDTO getVideoDetail(Long id);
    
    /**
     * 更新视频信息
     *
     * @param id 视频ID
     * @param title 视频标题
     * @param description 视频描述
     * @param cover 封面图
     * @param tags 标签
     * @param status 视频状态
     * @param isRecommended 是否推荐
     * @return 是否更新成功
     */
    boolean updateVideo(Long id, String title, String description, MultipartFile cover,
                      List<String> tags, String status, Boolean isRecommended);
    
    /**
     * 删除视频
     *
     * @param id 视频ID
     * @return 是否删除成功
     */
    boolean deleteVideo(Long id);
    
    /**
     * 设置视频访问权限
     *
     * @param id 视频ID
     * @param distributorIds 分销商ID列表
     * @return 权限设置结果
     */
    Map<String, Object> setVideoPermissions(Long id, List<Long> distributorIds);
} 