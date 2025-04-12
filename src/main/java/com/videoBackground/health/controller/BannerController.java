package com.videoBackground.health.controller;

import com.videoBackground.health.common.Result;
import com.videoBackground.health.service.BannerService;
import com.videoBackground.health.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Banner控制器
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    
    @Autowired
    private BannerService bannerService;
    
    /**
     * 获取首页轮播图
     *
     * @param type Banner类型
     * @param limit 数量限制
     * @return 轮播图列表
     */
    @GetMapping("/list")
    public Result<List<BannerVO>> getBannerList(
            @RequestParam(value = "type", required = false, defaultValue = "home") String type,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit) {
        List<BannerVO> list = bannerService.getBannerList(type, limit);
        return Result.success(list);
    }
} 