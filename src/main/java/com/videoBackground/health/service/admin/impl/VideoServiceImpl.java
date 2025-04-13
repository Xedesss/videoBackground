package com.videoBackground.health.service.admin.impl;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.video.VideoDTO;
import com.videoBackground.health.dto.video.VideoDetailDTO;
import com.videoBackground.health.dto.video.VideoListRequest;
import com.videoBackground.health.entity.Video;
import com.videoBackground.health.mapper.VideoMapper;
import com.videoBackground.health.service.admin.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 视频服务实现类
 */
@Service
public class VideoServiceImpl implements VideoService {
    
    @Autowired
    private VideoMapper videoMapper;
    
    // 模拟存储路径
    private static final String UPLOAD_DIR = "uploads";
    private static final String VIDEO_DIR = UPLOAD_DIR + "/videos";
    private static final String COVER_DIR = UPLOAD_DIR + "/covers";
    
    @Override
    public PageResult<VideoDTO> getVideoList(VideoListRequest request) {
        // 执行分页查询
        Integer offset = (request.getPage() - 1) * request.getSize();
        List<Video> videos = videoMapper.selectVideoList(
                offset, request.getSize(), request.getTitle(), 
                request.getStatus(), request.getOrderBy(), request.getOrderType());
        
        Long total = videoMapper.countVideoList(request.getTitle(), request.getStatus());
        
        // 转换为DTO
        List<VideoDTO> videoList = new ArrayList<>();
        for (Video video : videos) {
            VideoDTO dto = new VideoDTO();
            dto.setId(video.getId());
            dto.setTitle(video.getTitle());
            dto.setCover(video.getCover());
            dto.setDuration(video.getDuration());
            dto.setSize(video.getSize());
            dto.setUploadTime(video.getUploadTime());
            dto.setViewCount(video.getViewCount());
            dto.setStatus(video.getStatus());
            dto.setIsRecommended(video.getIsRecommended());
            videoList.add(dto);
        }
        
        return new PageResult<>(total, videoList);
    }
    
    @Override
    @Transactional
    public Map<String, Object> uploadVideo(String title, String description, String status,
                                         MultipartFile file, MultipartFile cover, List<String> tags) {
        try {
            // 确保上传目录存在
            createDirectories();
            
            // 生成文件名
            String videoFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String coverFileName = (cover != null) ? 
                    UUID.randomUUID() + "_" + cover.getOriginalFilename() : null;
            
            // 保存视频文件
            Path videoPath = Paths.get(VIDEO_DIR, videoFileName);
            file.transferTo(videoPath.toFile());
            
            // 保存封面文件
            String coverUrl = null;
            if (cover != null && !cover.isEmpty()) {
                Path coverPath = Paths.get(COVER_DIR, coverFileName);
                cover.transferTo(coverPath.toFile());
                coverUrl = "/api/files/covers/" + coverFileName;
            }
            
            // 计算视频时长（实际项目中应该使用视频处理库）
            int duration = 0; // 模拟数据
            
            // 创建视频对象
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setUrl("/api/files/videos/" + videoFileName);
            video.setCover(coverUrl);
            video.setDuration(duration);
            video.setSize(file.getSize());
            video.setUploadTime(new Date());
            video.setViewCount(0);
            video.setLikeCount(0);
            video.setCommentCount(0);
            video.setStatus(status);
            video.setIsRecommended(false);
            
            // 保存到数据库
            videoMapper.insertVideo(video);
            
            // 保存标签（如果有）
            if (tags != null && !tags.isEmpty()) {
                videoMapper.insertVideoTags(video.getId(), tags);
            }
            
            // 返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("id", video.getId());
            result.put("title", video.getTitle());
            result.put("url", video.getUrl());
            result.put("cover", video.getCover());
            result.put("uploadTime", video.getUploadTime());
            
            return result;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
    
    @Override
    public VideoDetailDTO getVideoDetail(Long id) {
        // 查询视频信息
        Video video = videoMapper.selectVideoById(id);
        if (video == null) {
            return null;
        }
        
        // 查询标签
        List<String> tags = videoMapper.selectVideoTags(id);
        
        // 查询分销商
        List<Map<String, Object>> distributors = videoMapper.selectVideoDistributors(id);
        
        // 转换为DTO
        VideoDetailDTO dto = new VideoDetailDTO();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setUrl(video.getUrl());
        dto.setCover(video.getCover());
        dto.setDuration(video.getDuration());
        dto.setSize(video.getSize());
        dto.setUploadTime(video.getUploadTime());
        dto.setViewCount(video.getViewCount());
        dto.setLikeCount(video.getLikeCount());
        dto.setCommentCount(video.getCommentCount());
        dto.setStatus(video.getStatus());
        dto.setIsRecommended(video.getIsRecommended());
        dto.setTags(tags);
        dto.setDistributors(distributors);
        
        return dto;
    }
    
    @Override
    @Transactional
    public boolean updateVideo(Long id, String title, String description, MultipartFile cover,
                             List<String> tags, String status, Boolean isRecommended) {
        // 查询视频是否存在
        Video video = videoMapper.selectVideoById(id);
        if (video == null) {
            return false;
        }
        
        // 处理封面上传
        if (cover != null && !cover.isEmpty()) {
            try {
                // 确保目录存在
                createDirectories();
                
                // 生成新的封面文件名
                String coverFileName = UUID.randomUUID() + "_" + cover.getOriginalFilename();
                Path coverPath = Paths.get(COVER_DIR, coverFileName);
                
                // 保存新封面
                cover.transferTo(coverPath.toFile());
                
                // 删除旧封面（如果存在）
                if (video.getCover() != null && !video.getCover().isEmpty()) {
                    String oldCoverPath = video.getCover().replace("/api/files/covers/", "");
                    Files.deleteIfExists(Paths.get(COVER_DIR, oldCoverPath));
                }
                
                // 更新封面URL
                video.setCover("/api/files/covers/" + coverFileName);
            } catch (IOException e) {
                throw new RuntimeException("封面上传失败", e);
            }
        }
        
        // 更新视频信息
        if (title != null) {
            video.setTitle(title);
        }
        if (description != null) {
            video.setDescription(description);
        }
        if (status != null) {
            video.setStatus(status);
        }
        if (isRecommended != null) {
            video.setIsRecommended(isRecommended);
        }
        
        // 更新到数据库
        videoMapper.updateVideo(video);
        
        // 更新标签（如果有）
        if (tags != null) {
            // 先删除旧标签
            videoMapper.deleteVideoTags(id);
            // 添加新标签
            if (!tags.isEmpty()) {
                videoMapper.insertVideoTags(id, tags);
            }
        }
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteVideo(Long id) {
        // 查询视频是否存在
        Video video = videoMapper.selectVideoById(id);
        if (video == null) {
            return false;
        }
        
        try {
            // 删除视频文件
            if (video.getUrl() != null && !video.getUrl().isEmpty()) {
                String videoPath = video.getUrl().replace("/api/files/videos/", "");
                Files.deleteIfExists(Paths.get(VIDEO_DIR, videoPath));
            }
            
            // 删除封面文件
            if (video.getCover() != null && !video.getCover().isEmpty()) {
                String coverPath = video.getCover().replace("/api/files/covers/", "");
                Files.deleteIfExists(Paths.get(COVER_DIR, coverPath));
            }
            
            // 删除数据库记录
            videoMapper.deleteVideoTags(id);
            videoMapper.deleteVideoDistributors(id);
            videoMapper.deleteVideo(id);
            
            return true;
        } catch (IOException e) {
            throw new RuntimeException("删除视频文件失败", e);
        }
    }
    
    @Override
    @Transactional
    public Map<String, Object> setVideoPermissions(Long id, List<Long> distributorIds) {
        // 查询视频是否存在
        Video video = videoMapper.selectVideoById(id);
        if (video == null) {
            return null;
        }
        
        // 删除旧的分配关系
        videoMapper.deleteVideoDistributors(id);
        
        // 建立新的分配关系
        if (distributorIds != null && !distributorIds.isEmpty()) {
            videoMapper.insertVideoDistributors(id, distributorIds);
        }
        
        // 查询分销商信息
        List<Map<String, Object>> distributors = videoMapper.selectVideoDistributors(id);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("videoId", id);
        result.put("distributors", distributors);
        
        return result;
    }
    
    /**
     * 创建上传目录
     */
    private void createDirectories() throws IOException {
        Files.createDirectories(Paths.get(VIDEO_DIR));
        Files.createDirectories(Paths.get(COVER_DIR));
    }
} 