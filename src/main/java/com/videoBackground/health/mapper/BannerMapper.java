package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Banner Mapper接口
 */
public interface BannerMapper {
    
    /**
     * 获取Banner列表
     *
     * @param type Banner类型
     * @param limit 数量限制
     * @return Banner列表
     */
    List<Banner> getBannerList(@Param("type") String type, @Param("limit") Integer limit);
} 