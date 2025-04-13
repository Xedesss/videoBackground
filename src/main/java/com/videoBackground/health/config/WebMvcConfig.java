package com.videoBackground.health.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    // 上传文件的根目录
    private static final String UPLOAD_DIR = "uploads";
    
    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 视频文件映射
        registry.addResourceHandler("/api/files/videos/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/videos/");
        
        // 图片文件映射
        registry.addResourceHandler("/api/files/images/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/images/");
        
        // 封面图片映射
        registry.addResourceHandler("/api/files/covers/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/covers/");
        
        // 二维码图片映射
        registry.addResourceHandler("/api/files/qrcodes/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/qrcodes/");
        
        // 文档文件映射
        registry.addResourceHandler("/api/files/documents/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/documents/");
    }
    
    /**
     * 配置跨域请求
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
} 