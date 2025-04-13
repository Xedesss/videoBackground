package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.Distributor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 分销商Mapper接口
 */
@Mapper
public interface DistributorMapper {
    
    /**
     * 分页查询分销商列表
     *
     * @param offset 偏移量
     * @param limit 查询数量
     * @param name 名称搜索
     * @param type 类型筛选
     * @param status 状态筛选
     * @return 分销商列表
     */
    List<Distributor> selectDistributorList(
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("name") String name,
            @Param("type") String type,
            @Param("status") String status);
    
    /**
     * 查询符合条件的分销商总数
     *
     * @param name 名称搜索
     * @param type 类型筛选
     * @param status 状态筛选
     * @return 总数
     */
    Long countDistributorList(
            @Param("name") String name,
            @Param("type") String type,
            @Param("status") String status);
    
    /**
     * 根据ID查询分销商
     *
     * @param id 分销商ID
     * @return 分销商信息
     */
    @Select("SELECT * FROM distributor WHERE id = #{id}")
    Distributor selectDistributorById(@Param("id") Long id);
    
    /**
     * 根据名称查询分销商
     *
     * @param name 分销商名称
     * @return 分销商信息
     */
    @Select("SELECT * FROM distributor WHERE name = #{name}")
    Distributor selectDistributorByName(@Param("name") String name);
    
    /**
     * 插入分销商
     *
     * @param distributor 分销商信息
     * @return 影响行数
     */
    int insertDistributor(Distributor distributor);
    
    /**
     * 更新分销商
     *
     * @param distributor 分销商信息
     * @return 影响行数
     */
    int updateDistributor(Distributor distributor);
    
    /**
     * 删除分销商
     *
     * @param id 分销商ID
     * @return 影响行数
     */
    int deleteDistributor(@Param("id") Long id);
    
    /**
     * 查询子分销商
     *
     * @param parentId 父级分销商ID
     * @return 子分销商列表
     */
    List<Distributor> selectChildDistributors(@Param("parentId") Long parentId);
    
    /**
     * 查询父级分销商
     *
     * @param id 分销商ID
     * @return 父级分销商
     */
    Distributor selectParentDistributor(@Param("id") Long id);
    
    /**
     * 查询分销商可访问的视频
     *
     * @param distributorId 分销商ID
     * @return 视频列表
     */
    List<Map<String, Object>> selectDistributorVideos(@Param("distributorId") Long distributorId);
    
    /**
     * 生成渠道码
     *
     * @param type 分销商类型
     * @return 渠道码
     */
    @Select("SELECT CASE " +
            "WHEN #{type} = 'general' THEN CONCAT('GD', LPAD(IFNULL(MAX(SUBSTRING(channel_code, 3)), 0) + 1, 3, '0')) " +
            "ELSE CONCAT('DS', LPAD(IFNULL(MAX(SUBSTRING(channel_code, 3)), 0) + 1, 3, '0')) " +
            "END " +
            "FROM distributor WHERE type = #{type}")
    String generateChannelCode(@Param("type") String type);
} 