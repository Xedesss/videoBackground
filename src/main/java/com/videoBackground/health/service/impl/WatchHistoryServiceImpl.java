package com.videoBackground.health.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.mapper.WatchHistoryMapper;
import com.videoBackground.health.service.WatchHistoryService;
import com.videoBackground.health.vo.WatchHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 观看记录服务实现类
 */
@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {
    
    @Autowired
    private WatchHistoryMapper watchHistoryMapper;
    
    /**
     * 获取用户观看记录
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 观看记录分页结果
     */
    @Override
    public PageResult<WatchHistoryVO> getUserWatchHistory(Long userId, Integer page, Integer pageSize) {
        // 参数检查和默认值设置
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        
        // 分页查询
        PageHelper.startPage(page, pageSize);
        List<WatchHistoryVO> historyList = watchHistoryMapper.getUserWatchHistory(userId);
        PageInfo<WatchHistoryVO> pageInfo = new PageInfo<>(historyList);
        
        // 处理时间显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        
        for (WatchHistoryVO history : historyList) {
            // 格式化观看时间为"x天前观看"或"x小时前观看"等格式
            if (history.getWatchTime() != null) {
                String formattedWatchTime = formatWatchTime(history.getWatchTime(), now);
                history.setWatchTime(formattedWatchTime);
            }
            
            // 如果视频时长为null或空字符串，设置为默认值"00:00"
            if (history.getDuration() == null || history.getDuration().isEmpty()) {
                history.setDuration("00:00");
            }
        }
        
        return new PageResult<>(page, pageSize, pageInfo.getTotal(), historyList);
    }
    
    /**
     * 将日期字符串格式化为"x天前观看"或"x小时前观看"等格式
     * 
     * @param watchTimeStr 观看时间字符串
     * @param now 当前时间
     * @return 格式化后的字符串
     */
    private String formatWatchTime(String watchTimeStr, Date now) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date watchTime = dateFormat.parse(watchTimeStr);
            
            long diff = now.getTime() - watchTime.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            long hours = diff / (60 * 60 * 1000) % 24;
            
            if (days > 0) {
                return days + "天前观看";
            } else if (hours > 0) {
                return hours + "小时前观看";
            } else {
                return "刚刚观看";
            }
        } catch (Exception e) {
            return watchTimeStr;
        }
    }
} 