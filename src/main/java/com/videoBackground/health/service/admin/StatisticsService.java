package com.videoBackground.health.service.admin;

import java.util.Map;

/**
 * 统计服务接口
 */
public interface StatisticsService {
    
    /**
     * 获取系统总体数据概览
     *
     * @return 概览数据
     */
    Map<String, Object> getOverviewData();
    
    /**
     * 获取用户增长趋势数据
     *
     * @param period 时间周期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 增长趋势数据
     */
    Map<String, Object> getUserGrowthData(String period, String startDate, String endDate);
    
    /**
     * 获取视频数据分析
     *
     * @param period 时间周期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分析数据
     */
    Map<String, Object> getVideoAnalysisData(String period, String startDate, String endDate);
    
    /**
     * 获取渠道转化率数据
     *
     * @param period 时间周期
     * @return 转化率数据
     */
    Map<String, Object> getChannelConversionData(String period);
    
    /**
     * 获取实时在线用户数据
     *
     * @return 在线用户数据
     */
    Map<String, Object> getOnlineUsersData();
} 