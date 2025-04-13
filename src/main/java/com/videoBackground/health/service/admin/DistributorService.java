package com.videoBackground.health.service.admin;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.distributor.DistributorCreateRequest;
import com.videoBackground.health.dto.distributor.DistributorDTO;
import com.videoBackground.health.dto.distributor.DistributorDetailDTO;
import com.videoBackground.health.dto.distributor.DistributorListRequest;
import com.videoBackground.health.dto.distributor.DistributorUpdateRequest;

import java.util.Map;

/**
 * 分销商服务接口
 */
public interface DistributorService {
    
    /**
     * 获取分销商列表
     *
     * @param request 请求参数
     * @return 分销商列表
     */
    PageResult<DistributorDTO> getDistributorList(DistributorListRequest request);
    
    /**
     * 创建分销商
     *
     * @param request 创建请求
     * @return 创建结果
     */
    Map<String, Object> createDistributor(DistributorCreateRequest request);
    
    /**
     * 获取分销商详情
     *
     * @param id 分销商ID
     * @return 分销商详情
     */
    DistributorDetailDTO getDistributorDetail(Long id);
    
    /**
     * 更新分销商信息
     *
     * @param id 分销商ID
     * @param request 更新请求
     * @return 是否更新成功
     */
    boolean updateDistributor(Long id, DistributorUpdateRequest request);
    
    /**
     * 删除分销商
     *
     * @param id 分销商ID
     * @return 是否删除成功
     */
    boolean deleteDistributor(Long id);
    
    /**
     * 获取分销商渠道二维码
     *
     * @param id 分销商ID
     * @return 二维码信息
     */
    Map<String, Object> getDistributorQrcode(Long id);
} 