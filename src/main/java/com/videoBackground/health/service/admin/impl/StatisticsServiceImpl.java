package com.videoBackground.health.service.admin.impl;

import com.videoBackground.health.mapper.DistributorMapper;
import com.videoBackground.health.mapper.VideoMapper;
import com.videoBackground.health.service.admin.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计服务实现类
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    
    @Autowired
    private VideoMapper videoMapper;
    
    @Autowired
    private DistributorMapper distributorMapper;
    
    @Override
    public Map<String, Object> getOverviewData() {
        // 实际项目中应该从数据库查询真实数据
        // 这里使用模拟数据
        Map<String, Object> data = new HashMap<>();
        
        data.put("totalUsers", 12680);
        data.put("totalVideos", 256);
        data.put("totalViews", 1356000);
        data.put("totalDistributors", 50);
        data.put("activeUsers", 3560);
        data.put("newUsersToday", 128);
        data.put("viewsToday", 5680);
        
        return data;
    }
    
    @Override
    public Map<String, Object> getUserGrowthData(String period, String startDate, String endDate) {
        // 实际项目中应该根据时间周期和日期范围从数据库查询
        // 这里使用模拟数据
        Map<String, Object> data = new HashMap<>();
        
        // 根据时间周期生成时间标签
        List<String> timeLabels = generateTimeLabels(period, startDate, endDate);
        data.put("timeLabels", timeLabels);
        
        // 模拟两组数据：新增用户和累计用户
        List<Map<String, Object>> series = new ArrayList<>();
        
        // 新增用户数据
        Map<String, Object> newUserSeries = new HashMap<>();
        newUserSeries.put("name", "新增用户");
        List<Integer> newUserData = Arrays.asList(1245, 1380, 1560, 1680, 1420, 1580);
        newUserSeries.put("data", newUserData);
        series.add(newUserSeries);
        
        // 累计用户数据
        Map<String, Object> totalUserSeries = new HashMap<>();
        totalUserSeries.put("name", "累计用户");
        List<Integer> totalUserData = Arrays.asList(8500, 9880, 11440, 13120, 14540, 16120);
        totalUserSeries.put("data", totalUserData);
        series.add(totalUserSeries);
        
        data.put("series", series);
        
        return data;
    }
    
    @Override
    public Map<String, Object> getVideoAnalysisData(String period, String startDate, String endDate) {
        // 实际项目中应该根据时间周期和日期范围从数据库查询
        // 这里使用模拟数据
        Map<String, Object> data = new HashMap<>();
        
        // 根据时间周期生成时间标签
        List<String> timeLabels = generateTimeLabels(period, startDate, endDate);
        data.put("timeLabels", timeLabels);
        
        // 视频播放量数据
        List<Integer> viewsSeries = Arrays.asList(58600, 62400, 75300, 84500, 79200, 86400);
        data.put("viewsSeries", viewsSeries);
        
        // 热门视频排行
        List<Map<String, Object>> topVideos = new ArrayList<>();
        
        Map<String, Object> video1 = new HashMap<>();
        video1.put("id", 1);
        video1.put("title", "养生太极教学");
        video1.put("views", 12680);
        video1.put("likes", 3420);
        topVideos.add(video1);
        
        Map<String, Object> video2 = new HashMap<>();
        video2.put("id", 2);
        video2.put("title", "八段锦详解");
        video2.put("views", 10240);
        video2.put("likes", 2860);
        topVideos.add(video2);
        
        Map<String, Object> video3 = new HashMap<>();
        video3.put("id", 3);
        video3.put("title", "五禽戏演示");
        video3.put("views", 9650);
        video3.put("likes", 2540);
        topVideos.add(video3);
        
        data.put("topVideos", topVideos);
        
        // 分类分布
        List<Map<String, Object>> categoryDistribution = new ArrayList<>();
        
        Map<String, Object> category1 = new HashMap<>();
        category1.put("name", "太极");
        category1.put("value", 35);
        categoryDistribution.add(category1);
        
        Map<String, Object> category2 = new HashMap<>();
        category2.put("name", "气功");
        category2.put("value", 28);
        categoryDistribution.add(category2);
        
        Map<String, Object> category3 = new HashMap<>();
        category3.put("name", "养生操");
        category3.put("value", 22);
        categoryDistribution.add(category3);
        
        Map<String, Object> category4 = new HashMap<>();
        category4.put("name", "其他");
        category4.put("value", 15);
        categoryDistribution.add(category4);
        
        data.put("categoryDistribution", categoryDistribution);
        
        return data;
    }
    
    @Override
    public Map<String, Object> getChannelConversionData(String period) {
        // 实际项目中应该根据时间周期从数据库查询
        // 这里使用模拟数据
        Map<String, Object> data = new HashMap<>();
        
        // 总体转化率
        data.put("totalConversion", 3.6);
        
        // 各渠道数据
        List<Map<String, Object>> channels = new ArrayList<>();
        
        Map<String, Object> channel1 = new HashMap<>();
        channel1.put("id", 1);
        channel1.put("name", "华北总经销");
        channel1.put("visits", 12580);
        channel1.put("registrations", 560);
        channel1.put("conversion", 4.5);
        channel1.put("orders", 128);
        channels.add(channel1);
        
        Map<String, Object> channel2 = new HashMap<>();
        channel2.put("id", 2);
        channel2.put("name", "华东总经销");
        channel2.put("visits", 15620);
        channel2.put("registrations", 620);
        channel2.put("conversion", 4.0);
        channel2.put("orders", 145);
        channels.add(channel2);
        
        Map<String, Object> channel3 = new HashMap<>();
        channel3.put("id", 3);
        channel3.put("name", "西南总经销");
        channel3.put("visits", 9850);
        channel3.put("registrations", 320);
        channel3.put("conversion", 3.2);
        channel3.put("orders", 86);
        channels.add(channel3);
        
        data.put("channels", channels);
        
        return data;
    }
    
    @Override
    public Map<String, Object> getOnlineUsersData() {
        // 实际项目中应该从缓存或实时数据源获取
        // 这里使用模拟数据
        Map<String, Object> data = new HashMap<>();
        
        // 当前在线人数
        data.put("currentOnline", 356);
        
        // 今日峰值
        data.put("todayPeak", 580);
        
        // 小时数据
        List<Map<String, Object>> hourlyData = new ArrayList<>();
        String[] hours = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", 
                           "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
                           "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
                           "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        
        // 生成模拟的小时数据
        Random random = new Random();
        for (String hour : hours) {
            Map<String, Object> hourData = new HashMap<>();
            hourData.put("hour", hour);
            
            // 根据时间段生成不同范围的在线人数
            int count;
            if (hour.startsWith("0") || hour.startsWith("1") || hour.startsWith("2") || hour.startsWith("3") || hour.startsWith("4")) {
                // 深夜到凌晨人数较少
                count = 50 + random.nextInt(100);
            } else if (hour.startsWith("5") || hour.startsWith("6") || hour.startsWith("7")) {
                // 早晨人数开始增加
                count = 100 + random.nextInt(150);
            } else if (hour.startsWith("8") || hour.startsWith("9") || hour.startsWith("10") || hour.startsWith("11")) {
                // 上午人数较多
                count = 200 + random.nextInt(200);
            } else if (hour.startsWith("12") || hour.startsWith("13")) {
                // 中午人数略减
                count = 150 + random.nextInt(150);
            } else if (hour.startsWith("14") || hour.startsWith("15") || hour.startsWith("16") || hour.startsWith("17")) {
                // 下午人数适中
                count = 180 + random.nextInt(180);
            } else if (hour.startsWith("18") || hour.startsWith("19") || hour.startsWith("20") || hour.startsWith("21")) {
                // 晚上人数最多
                count = 250 + random.nextInt(330);
            } else {
                // 深夜人数开始减少
                count = 120 + random.nextInt(150);
            }
            
            hourData.put("count", count);
            hourlyData.add(hourData);
        }
        
        data.put("hourlyData", hourlyData);
        
        // 设备分布
        List<Map<String, Object>> deviceDistribution = new ArrayList<>();
        
        Map<String, Object> device1 = new HashMap<>();
        device1.put("name", "iOS");
        device1.put("value", 45);
        deviceDistribution.add(device1);
        
        Map<String, Object> device2 = new HashMap<>();
        device2.put("name", "Android");
        device2.put("value", 40);
        deviceDistribution.add(device2);
        
        Map<String, Object> device3 = new HashMap<>();
        device3.put("name", "Web");
        device3.put("value", 15);
        deviceDistribution.add(device3);
        
        data.put("deviceDistribution", deviceDistribution);
        
        return data;
    }
    
    /**
     * 根据时间周期和日期范围生成时间标签
     */
    private List<String> generateTimeLabels(String period, String startDate, String endDate) {
        List<String> timeLabels = new ArrayList<>();
        
        // 根据不同的时间周期生成不同的标签
        switch (period) {
            case "day":
                // 时间格式：YYYY-MM-DD
                timeLabels.addAll(Arrays.asList(
                        "2024-04-08", "2024-04-09", "2024-04-10", 
                        "2024-04-11", "2024-04-12", "2024-04-13"));
                break;
            case "week":
                // 时间格式：YYYY-WW
                timeLabels.addAll(Arrays.asList(
                        "2024-W10", "2024-W11", "2024-W12", 
                        "2024-W13", "2024-W14", "2024-W15"));
                break;
            case "month":
                // 时间格式：YYYY-MM
                timeLabels.addAll(Arrays.asList(
                        "2023-10", "2023-11", "2023-12", 
                        "2024-01", "2024-02", "2024-03"));
                break;
            case "year":
                // 时间格式：YYYY
                timeLabels.addAll(Arrays.asList(
                        "2019", "2020", "2021", "2022", "2023", "2024"));
                break;
            default:
                // 默认使用月份标签
                timeLabels.addAll(Arrays.asList(
                        "2023-10", "2023-11", "2023-12", 
                        "2024-01", "2024-02", "2024-03"));
                break;
        }
        
        return timeLabels;
    }
} 