package com.videoBackground.health.service.impl;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.entity.Video;
import com.videoBackground.health.mapper.VideoMapper;
import com.videoBackground.health.mapper.WatchHistoryMapper;
import com.videoBackground.health.service.ClientVideoService;
import com.videoBackground.health.vo.VideoDetailVO;
import com.videoBackground.health.vo.VideoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户端视频服务实现类
 */
@Service
public class ClientVideoServiceImpl implements ClientVideoService {
    
    @Autowired
    private VideoMapper videoMapper;
    
    @Autowired
    private WatchHistoryMapper watchHistoryMapper;
    
    @Override
    public PageResult<VideoListVO> getVideoList(Integer page, Integer pageSize, String keyword, 
                                             String status, String courseType, 
                                             String sortField, String sortOrder) {
        // 计算偏移量
        Integer offset = (page - 1) * pageSize;
        
        // 查询视频列表
        List<Video> videos = videoMapper.selectVideoList(offset, pageSize, keyword, status, sortField, sortOrder);
        
        // 转换为前端需要的格式
        List<VideoListVO> videoListVOS = convertToListVO(videos);
        
        // 查询总记录数
        Long total = videoMapper.countVideoList(keyword, status);
        
        return new PageResult<>(total, videoListVOS);
    }
    
    @Override
    public VideoDetailVO getVideoDetail(Long id) {
        // 查询视频详情
        Video video = videoMapper.selectVideoById(id);
        
        // 如果视频存在，更新浏览量并转换为前端需要的格式
        if (video != null) {
            videoMapper.incrementViewCount(id);
            return convertToDetailVO(video);
        }
        
        return null;
    }
    
    @Override
    @Transactional
    public double reportVideoProgress(Long videoId, Long userId, Integer watchTime, Integer totalTime) {
        // 计算观看进度
        double progress = Math.min(1.0, (double) watchTime / totalTime);
        
        // 更新观看记录
        int updated = watchHistoryMapper.updateWatchHistory(videoId, userId, watchTime, totalTime, progress, new Date());
        
        // 如果没有更新记录，则创建新记录
        if (updated == 0) {
            watchHistoryMapper.insertWatchHistory(videoId, userId, watchTime, totalTime, progress, new Date());
        }
        
        return progress;
    }
    
    /**
     * 将视频实体转换为列表视图对象
     */
    private List<VideoListVO> convertToListVO(List<Video> videos) {
        // 实际项目中这里应该有复杂的转换逻辑
        // 这里简化处理
        List<VideoListVO> result = new ArrayList<>();
        for (Video video : videos) {
            VideoListVO vo = new VideoListVO();
            // 设置VO属性
            // vo.setXxx(video.getXxx())
            result.add(vo);
        }
        return result;
    }
    
    /**
     * 将视频实体转换为详情视图对象
     */
    private VideoDetailVO convertToDetailVO(Video video) {
        // 实际项目中这里应该有复杂的转换逻辑
        // 这里简化处理
        VideoDetailVO vo = new VideoDetailVO();
        // 设置VO属性
        // vo.setXxx(video.getXxx())
        return vo;
    }
} 