package com.videoBackground.health.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("com.videoBackground.health.mapper")
public class MyBatisConfig {
    
} 