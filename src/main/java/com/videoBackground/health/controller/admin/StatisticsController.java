package com.videoBackground.health.controller.admin;

import com.videoBackground.health.common.ApiResponse;
import com.videoBackground.health.service.admin.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据统计控制器
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    /**
     * 获取系统总体数据概览
     *
     * @return 数据概览
     */
    @GetMapping("/overview")
    public ApiResponse<Map<String, Object>> getOverview() {
        Map<String, Object> overviewData = statisticsService.getOverviewData();
        return ApiResponse.success(overviewData);
    }
    
    /**
     * 获取用户增长趋势
     *
     * @param period 时间周期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 增长趋势数据
     */
    @GetMapping("/user-growth")
    public ApiResponse<Map<String, Object>> getUserGrowth(
            @RequestParam(value = "period", required = false, defaultValue = "month") String period,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        
        Map<String, Object> growthData = statisticsService.getUserGrowthData(period, startDate, endDate);
        return ApiResponse.success(growthData);
    }
    
    /**
     * 获取视频数据分析
     *
     * @param period 时间周期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 视频数据分析
     */
    @GetMapping("/video-analysis")
    public ApiResponse<Map<String, Object>> getVideoAnalysis(
            @RequestParam(value = "period", required = false, defaultValue = "month") String period,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        
        Map<String, Object> analysisData = statisticsService.getVideoAnalysisData(period, startDate, endDate);
        return ApiResponse.success(analysisData);
    }
    
    /**
     * 获取渠道转化率
     *
     * @param period 时间周期
     * @return 渠道转化率数据
     */
    @GetMapping("/channel-conversion")
    public ApiResponse<Map<String, Object>> getChannelConversion(
            @RequestParam(value = "period", required = false, defaultValue = "month") String period) {
        
        Map<String, Object> conversionData = statisticsService.getChannelConversionData(period);
        return ApiResponse.success(conversionData);
    }
    
    /**
     * 获取实时在线用户
     *
     * @return 在线用户数据
     */
    @GetMapping("/online-users")
    public ApiResponse<Map<String, Object>> getOnlineUsers() {
        Map<String, Object> onlineData = statisticsService.getOnlineUsersData();
        return ApiResponse.success(onlineData);
    }
} 