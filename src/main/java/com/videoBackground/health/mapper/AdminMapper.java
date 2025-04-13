package com.videoBackground.health.mapper;

import com.videoBackground.health.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员Mapper接口
 */
@Mapper
public interface AdminMapper {
    
    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return 管理员信息
     */
    @Select("SELECT * FROM admin WHERE username = #{username} AND status = 1")
    Admin selectByUsername(@Param("username") String username);
    
    /**
     * 更新最后登录时间
     *
     * @param id 管理员ID
     * @return 影响行数
     */
    int updateLastLoginTime(@Param("id") Long id);
} 