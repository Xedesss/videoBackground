package com.videoBackground.health.service.admin.impl;

import com.videoBackground.health.common.PageResult;
import com.videoBackground.health.dto.distributor.DistributorCreateRequest;
import com.videoBackground.health.dto.distributor.DistributorDTO;
import com.videoBackground.health.dto.distributor.DistributorDetailDTO;
import com.videoBackground.health.dto.distributor.DistributorListRequest;
import com.videoBackground.health.dto.distributor.DistributorUpdateRequest;
import com.videoBackground.health.entity.Distributor;
import com.videoBackground.health.mapper.DistributorMapper;
import com.videoBackground.health.service.admin.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分销商服务实现类
 */
@Service
public class DistributorServiceImpl implements DistributorService {
    
    @Autowired
    private DistributorMapper distributorMapper;
    
    // 模拟存储路径
    private static final String UPLOAD_DIR = "uploads";
    private static final String QRCODE_DIR = UPLOAD_DIR + "/qrcodes";
    
    @Override
    public PageResult<DistributorDTO> getDistributorList(DistributorListRequest request) {
        // 执行分页查询
        Integer offset = (request.getPage() - 1) * request.getSize();
        List<Distributor> distributors = distributorMapper.selectDistributorList(
                offset, request.getSize(), request.getName(), 
                request.getType(), request.getStatus());
        
        Long total = distributorMapper.countDistributorList(
                request.getName(), request.getType(), request.getStatus());
        
        // 构建树形结构
        Map<Long, DistributorDTO> dtoMap = new HashMap<>();
        List<DistributorDTO> rootList = new ArrayList<>();
        
        // 转换为DTO并建立映射
        for (Distributor distributor : distributors) {
            DistributorDTO dto = convertToDTO(distributor);
            dtoMap.put(dto.getId(), dto);
            
            // 父级为空的是根节点
            if (distributor.getParentId() == null) {
                rootList.add(dto);
            }
        }
        
        // 建立父子关系
        for (Distributor distributor : distributors) {
            if (distributor.getParentId() != null) {
                DistributorDTO parentDto = dtoMap.get(distributor.getParentId());
                DistributorDTO childDto = dtoMap.get(distributor.getId());
                
                if (parentDto != null && childDto != null) {
                    if (parentDto.getChildren() == null) {
                        parentDto.setChildren(new ArrayList<>());
                    }
                    parentDto.getChildren().add(childDto);
                }
            }
        }
        
        return new PageResult<>(total, rootList);
    }
    
    @Override
    @Transactional
    public Map<String, Object> createDistributor(DistributorCreateRequest request) {
        // 检查名称是否已存在
        Distributor existing = distributorMapper.selectDistributorByName(request.getName());
        if (existing != null) {
            throw new RuntimeException("分销商名称已存在");
        }
        
        // 检查父级分销商是否存在
        if (request.getParentId() != null) {
            Distributor parent = distributorMapper.selectDistributorById(request.getParentId());
            if (parent == null) {
                throw new RuntimeException("父级分销商不存在");
            }
        }
        
        // 生成渠道码
        String channelCode = distributorMapper.generateChannelCode(request.getType());
        
        // 创建分销商
        Distributor distributor = new Distributor();
        distributor.setName(request.getName());
        distributor.setType(request.getType());
        distributor.setContact(request.getContact());
        distributor.setPhone(request.getPhone());
        distributor.setParentId(request.getParentId());
        distributor.setStatus(request.getStatus());
        distributor.setPassword(request.getPassword()); // 实际项目中应进行加密
        distributor.setChannelCode(channelCode);
        distributor.setCreateTime(new Date());
        distributor.setCustomerCount(0);
        distributor.setOrderCount(0);
        
        // 保存到数据库
        distributorMapper.insertDistributor(distributor);
        
        // 生成二维码（实际项目中应调用二维码生成库）
        generateQrcode(distributor.getId(), channelCode);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("id", distributor.getId());
        result.put("name", distributor.getName());
        result.put("type", distributor.getType());
        result.put("channelCode", distributor.getChannelCode());
        result.put("createTime", distributor.getCreateTime());
        
        return result;
    }
    
    @Override
    public DistributorDetailDTO getDistributorDetail(Long id) {
        // 查询分销商
        Distributor distributor = distributorMapper.selectDistributorById(id);
        if (distributor == null) {
            return null;
        }
        
        // 查询父级分销商
        Distributor parent = null;
        if (distributor.getParentId() != null) {
            parent = distributorMapper.selectParentDistributor(distributor.getId());
        }
        
        // 查询子分销商
        List<Distributor> children = distributorMapper.selectChildDistributors(id);
        
        // 查询可访问的视频
        List<Map<String, Object>> accessibleVideos = distributorMapper.selectDistributorVideos(id);
        
        // 转换为DTO
        DistributorDetailDTO dto = new DistributorDetailDTO();
        dto.setId(distributor.getId());
        dto.setName(distributor.getName());
        dto.setType(distributor.getType());
        dto.setContact(distributor.getContact());
        dto.setPhone(distributor.getPhone());
        dto.setCreateTime(distributor.getCreateTime());
        dto.setCustomerCount(distributor.getCustomerCount());
        dto.setOrderCount(distributor.getOrderCount());
        dto.setStatus(distributor.getStatus());
        dto.setChannelCode(distributor.getChannelCode());
        
        // 设置父级分销商
        if (parent != null) {
            Map<String, Object> parentMap = new HashMap<>();
            parentMap.put("id", parent.getId());
            parentMap.put("name", parent.getName());
            parentMap.put("type", parent.getType());
            dto.setParent(parentMap);
        }
        
        // 设置子分销商
        if (children != null && !children.isEmpty()) {
            List<Map<String, Object>> childrenList = children.stream()
                    .map(child -> {
                        Map<String, Object> childMap = new HashMap<>();
                        childMap.put("id", child.getId());
                        childMap.put("name", child.getName());
                        childMap.put("type", child.getType());
                        return childMap;
                    })
                    .collect(Collectors.toList());
            dto.setChildren(childrenList);
        }
        
        // 设置可访问的视频
        dto.setAccessibleVideos(accessibleVideos);
        
        return dto;
    }
    
    @Override
    @Transactional
    public boolean updateDistributor(Long id, DistributorUpdateRequest request) {
        // 查询分销商是否存在
        Distributor distributor = distributorMapper.selectDistributorById(id);
        if (distributor == null) {
            return false;
        }
        
        // 检查名称是否已存在（如果修改了名称）
        if (request.getName() != null && !request.getName().equals(distributor.getName())) {
            Distributor existing = distributorMapper.selectDistributorByName(request.getName());
            if (existing != null) {
                throw new RuntimeException("分销商名称已存在");
            }
        }
        
        // 更新信息
        if (request.getName() != null) {
            distributor.setName(request.getName());
        }
        if (request.getContact() != null) {
            distributor.setContact(request.getContact());
        }
        if (request.getPhone() != null) {
            distributor.setPhone(request.getPhone());
        }
        if (request.getStatus() != null) {
            distributor.setStatus(request.getStatus());
        }
        if (request.getPassword() != null) {
            // 实际项目中应进行密码加密
            distributor.setPassword(request.getPassword());
        }
        
        // 更新到数据库
        distributorMapper.updateDistributor(distributor);
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteDistributor(Long id) {
        // 查询分销商是否存在
        Distributor distributor = distributorMapper.selectDistributorById(id);
        if (distributor == null) {
            return false;
        }
        
        // 检查是否有子分销商
        List<Distributor> children = distributorMapper.selectChildDistributors(id);
        if (children != null && !children.isEmpty()) {
            throw new RuntimeException("无法删除：该分销商下存在子分销商");
        }
        
        // 删除二维码
        try {
            deleteQrcode(distributor.getChannelCode());
        } catch (IOException e) {
            // 记录日志但不阻止删除
            e.printStackTrace();
        }
        
        // 删除分销商
        distributorMapper.deleteDistributor(id);
        
        return true;
    }
    
    @Override
    public Map<String, Object> getDistributorQrcode(Long id) {
        // 查询分销商
        Distributor distributor = distributorMapper.selectDistributorById(id);
        if (distributor == null) {
            return null;
        }
        
        // 二维码URL
        String qrcodeUrl = "/api/files/qrcodes/" + distributor.getChannelCode() + ".png";
        
        // 如果二维码不存在则生成
        Path qrcodePath = Paths.get(QRCODE_DIR, distributor.getChannelCode() + ".png");
        if (!Files.exists(qrcodePath)) {
            generateQrcode(id, distributor.getChannelCode());
        }
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("id", distributor.getId());
        result.put("channelCode", distributor.getChannelCode());
        result.put("qrcodeUrl", qrcodeUrl);
        
        return result;
    }
    
    /**
     * 生成二维码（模拟）
     */
    private void generateQrcode(Long distributorId, String channelCode) {
        try {
            // 确保目录存在
            Files.createDirectories(Paths.get(QRCODE_DIR));
            
            // 创建一个空文件模拟二维码
            Files.createFile(Paths.get(QRCODE_DIR, channelCode + ".png"));
        } catch (IOException e) {
            throw new RuntimeException("生成二维码失败", e);
        }
    }
    
    /**
     * 删除二维码
     */
    private void deleteQrcode(String channelCode) throws IOException {
        Files.deleteIfExists(Paths.get(QRCODE_DIR, channelCode + ".png"));
    }
    
    /**
     * 转换为DTO
     */
    private DistributorDTO convertToDTO(Distributor distributor) {
        if (distributor == null) {
            return null;
        }
        
        DistributorDTO dto = new DistributorDTO();
        dto.setId(distributor.getId());
        dto.setName(distributor.getName());
        dto.setType(distributor.getType());
        dto.setContact(distributor.getContact());
        dto.setPhone(distributor.getPhone());
        dto.setCreateTime(distributor.getCreateTime());
        dto.setCustomerCount(distributor.getCustomerCount());
        dto.setOrderCount(distributor.getOrderCount());
        dto.setStatus(distributor.getStatus());
        dto.setChannelCode(distributor.getChannelCode());
        dto.setParentId(distributor.getParentId());
        
        return dto;
    }
} 