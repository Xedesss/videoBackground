package com.videoBackground.health.service.impl;

import com.videoBackground.health.entity.Banner;
import com.videoBackground.health.mapper.BannerMapper;
import com.videoBackground.health.service.BannerService;
import com.videoBackground.health.vo.BannerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Banner服务实现类
 */
@Service
public class BannerServiceImpl implements BannerService {
    
    @Autowired
    private BannerMapper bannerMapper;
    
    /**
     * 获取Banner列表
     *
     * @param type Banner类型
     * @param limit 数量限制
     * @return Banner列表
     */
    @Override
    public List<BannerVO> getBannerList(String type, Integer limit) {
        // 默认返回5条
        if (limit == null || limit <= 0) {
            limit = 5;
        }
        
        // 查询数据
        List<Banner> bannerList = bannerMapper.getBannerList(type, limit);
        
        // 转换为VO
        return bannerList.stream().map(banner -> {
            BannerVO vo = new BannerVO();
            BeanUtils.copyProperties(banner, vo);
            return vo;
        }).collect(Collectors.toList());
    }
} 