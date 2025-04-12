package com.videoBackground.health.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.common.ResultCode;
import com.videoBackground.health.entity.Video;
import com.videoBackground.health.mapper.VideoMapper;
import com.videoBackground.health.mapper.WatchHistoryMapper;
import com.videoBackground.health.service.VideoService;
import com.videoBackground.health.vo.VideoDetailVO;
import com.videoBackground.health.vo.VideoListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频服务实现类
 */
@Service
public class VideoServiceImpl implements VideoService {
    
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
    
    @Autowired
    private VideoMapper videoMapper;
    
    @Autowired
    private WatchHistoryMapper watchHistoryMapper;
    
    /**
     * 获取视频列表
     */
    @Override
    public PageResult<VideoListVO> getVideoList(Integer page, Integer pageSize, String keyword, 
                                                String status, String courseType, 
                                                String sortField, String sortOrder) {
        // 设置默认值
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        
        // 使用PageHelper进行分页
        PageHelper.startPage(page, pageSize);
        List<Video> videoList = videoMapper.getVideoList(keyword, status, courseType, sortField, sortOrder);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        
        // 转换为VO对象
        List<VideoListVO> voList = videoList.stream().map(this::convertToListVO).collect(Collectors.toList());
        
        return new PageResult<>(page, pageSize, pageInfo.getTotal(), voList);
    }
    
    /**
     * 获取视频详情
     */
    @Override
    public VideoDetailVO getVideoDetail(Long id) {
        Video video = videoMapper.getVideoById(id);
        if (video == null) {
            logger.warn("视频不存在, id: {}", id);
            return null;
        }
        
        // 增加观看次数
        videoMapper.updateViewCount(id);
        
        // 获取问题ID列表
        List<Long> questionIds = new ArrayList<>();
        if (video.getHasQuestions()) {
            questionIds = videoMapper.getVideoQuestionIds(id);
        }
        
        // 获取相关视频
        List<Video> relatedVideos = videoMapper.getRelatedVideos(video.getCategoryId(), id, 5);
        
        // 转换为详情VO
        return convertToDetailVO(video, questionIds, relatedVideos);
    }
    
    /**
     * 上报视频观看进度
     */
    @Override
    public double reportVideoProgress(Long videoId, Long userId, Integer watchTime, Integer totalTime) {
        if (videoId == null || userId == null || watchTime == null || totalTime == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        
        // 计算进度百分比
        int progress = (int) Math.floor((double) watchTime / totalTime * 100);
        if (progress > 100) {
            progress = 100;
        }
        
        // 保存观看记录
        watchHistoryMapper.saveWatchHistory(userId, videoId, progress, watchTime);
        
        return progress;
    }
    
    /**
     * 转换为列表视图对象
     */
    private VideoListVO convertToListVO(Video video) {
        if (video == null) {
            return null;
        }
        
        VideoListVO vo = new VideoListVO();
        BeanUtils.copyProperties(video, vo);
        
        // 格式化日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        if (video.getStartTime() != null) {
            vo.setStartDate(dateFormat.format(video.getStartTime()));
            vo.setStartTime(timeFormat.format(video.getStartTime()));
        }
        
        if (video.getEndTime() != null) {
            vo.setEndTime(timeFormat.format(video.getEndTime()));
        }
        
        // 格式化时长
        if (video.getDuration() != null) {
            int duration = video.getDuration();
            int minutes = duration / 60;
            int seconds = duration % 60;
            vo.setDuration(String.format("%d:%02d", minutes, seconds));
        }
        
        // 格式化创建时间
        if (video.getCreateTime() != null) {
            vo.setCreateTime(dateFormat.format(video.getCreateTime()));
        }
        
        return vo;
    }
    
    /**
     * 转换为详情视图对象
     */
    private VideoDetailVO convertToDetailVO(Video video, List<Long> questionIds, List<Video> relatedVideos) {
        if (video == null) {
            return null;
        }
        
        VideoDetailVO vo = new VideoDetailVO();
        BeanUtils.copyProperties(video, vo);
        
        // 格式化日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        if (video.getStartTime() != null) {
            vo.setStartDate(dateFormat.format(video.getStartTime()));
            vo.setStartTime(timeFormat.format(video.getStartTime()));
        }
        
        if (video.getEndTime() != null) {
            vo.setEndTime(timeFormat.format(video.getEndTime()));
        }
        
        // 格式化时长
        if (video.getDuration() != null) {
            int duration = video.getDuration();
            int minutes = duration / 60;
            int seconds = duration % 60;
            vo.setDuration(String.format("%d:%02d", minutes, seconds));
        }
        
        // 格式化创建时间
        if (video.getCreateTime() != null) {
            vo.setCreateTime(dateFormat.format(video.getCreateTime()));
        }
        
        // 设置问题ID列表
        vo.setQuestionIds(questionIds);
        
        // 设置相关视频
        if (relatedVideos != null && !relatedVideos.isEmpty()) {
            List<VideoDetailVO.RelatedVideoVO> relatedVideoVOList = relatedVideos.stream().map(relatedVideo -> {
                VideoDetailVO.RelatedVideoVO relatedVideoVO = new VideoDetailVO.RelatedVideoVO();
                relatedVideoVO.setId(relatedVideo.getId());
                relatedVideoVO.setTitle(relatedVideo.getTitle());
                relatedVideoVO.setCoverUrl(relatedVideo.getCoverUrl());
                
                // 格式化时长
                if (relatedVideo.getDuration() != null) {
                    int duration = relatedVideo.getDuration();
                    int minutes = duration / 60;
                    int seconds = duration % 60;
                    relatedVideoVO.setDuration(String.format("%d:%02d", minutes, seconds));
                }
                
                return relatedVideoVO;
            }).collect(Collectors.toList());
            
            vo.setRelatedVideos(relatedVideoVOList);
        }
        
        return vo;
    }
} 