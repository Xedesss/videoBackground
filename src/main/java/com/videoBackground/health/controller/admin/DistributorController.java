package com.videoBackground.health.controller.admin;

import com.videoBackground.health.common.ApiResponse;
import com.videoBackground.health.common.ApiStatusCode;
import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.distributor.DistributorCreateRequest;
import com.videoBackground.health.dto.distributor.DistributorDTO;
import com.videoBackground.health.dto.distributor.DistributorDetailDTO;
import com.videoBackground.health.dto.distributor.DistributorListRequest;
import com.videoBackground.health.dto.distributor.DistributorUpdateRequest;
import com.videoBackground.health.service.admin.DistributorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 分销商控制器
 */
@RestController
@RequestMapping("/api/distributors")
public class DistributorController {
    
    @Autowired
    private DistributorService distributorService;
    
    /**
     * 获取分销商列表
     *
     * @param request 请求参数
     * @return 分销商列表
     */
    @GetMapping
    public ApiResponse<PageResult<DistributorDTO>> getDistributorList(DistributorListRequest request) {
        PageResult<DistributorDTO> result = distributorService.getDistributorList(request);
        return ApiResponse.success(result);
    }
    
    /**
     * 创建分销商
     *
     * @param request 创建请求
     * @return 创建结果
     */
    @PostMapping
    public ApiResponse<Map<String, Object>> createDistributor(@Valid @RequestBody DistributorCreateRequest request) {
        Map<String, Object> result = distributorService.createDistributor(request);
        return ApiResponse.success("创建成功", result);
    }
    
    /**
     * 获取分销商详情
     *
     * @param id 分销商ID
     * @return 分销商详情
     */
    @GetMapping("/{id}")
    public ApiResponse<DistributorDetailDTO> getDistributorDetail(@PathVariable("id") Long id) {
        DistributorDetailDTO detail = distributorService.getDistributorDetail(id);
        if (detail == null) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "分销商不存在");
        }
        return ApiResponse.success(detail);
    }
    
    /**
     * 更新分销商信息
     *
     * @param id 分销商ID
     * @param request 更新请求
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ApiResponse<Map<String, Object>> updateDistributor(
            @PathVariable("id") Long id,
            @RequestBody DistributorUpdateRequest request) {
        
        boolean success = distributorService.updateDistributor(id, request);
        
        if (!success) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "分销商不存在");
        }
        
        Map<String, Object> result = Map.of(
            "id", id,
            "name", request.getName(),
            "status", request.getStatus()
        );
        
        return ApiResponse.success("更新成功", result);
    }
    
    /**
     * 删除分销商
     *
     * @param id 分销商ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDistributor(@PathVariable("id") Long id) {
        boolean success = distributorService.deleteDistributor(id);
        
        if (!success) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "分销商不存在");
        }
        
        return ApiResponse.success("删除成功", null);
    }
    
    /**
     * 获取分销商渠道二维码
     *
     * @param id 分销商ID
     * @return 二维码信息
     */
    @GetMapping("/{id}/qrcode")
    public ApiResponse<Map<String, Object>> getDistributorQrcode(@PathVariable("id") Long id) {
        Map<String, Object> qrcodeInfo = distributorService.getDistributorQrcode(id);
        
        if (qrcodeInfo == null) {
            return ApiResponse.error(ApiStatusCode.NOT_FOUND, "分销商不存在");
        }
        
        return ApiResponse.success(qrcodeInfo);
    }
} 