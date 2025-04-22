package com.example.secondhandcar.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC配置
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.access-path}")
    private String accessPath;

    /**
     * 配置静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取上传路径的绝对路径
        File uploadDir = new File(uploadPath);
        String absolutePath = uploadDir.getAbsolutePath();
        
        log.info("配置文件上传路径: {}", absolutePath);
        
        // 确保上传目录存在
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            log.info("创建上传目录: {}, 结果: {}", absolutePath, created ? "成功" : "失败");
        }
        
        // 配置上传文件存储路径访问
        registry.addResourceHandler(accessPath)
                .addResourceLocations("file:" + absolutePath + "/");
        
        log.info("文件访问路径: {}, 资源位置: file:{}/", accessPath, absolutePath);
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 将根路径重定向到Swagger UI页面
        registry.addRedirectViewController("/", "/swagger-ui.html");
        // 将context-path路径重定向到Swagger UI页面
        registry.addRedirectViewController("/api", "/swagger-ui.html");
    }
    
    /**
     * 应用启动时创建必要的上传目录
     */
    @Bean
    public CommandLineRunner initUploadDirs() {
        return args -> {
            // 创建主上传目录
            File mainUploadDir = new File(uploadPath);
            if (!mainUploadDir.exists()) {
                boolean created = mainUploadDir.mkdirs();
                log.info("创建主上传目录: {}, 结果: {}", mainUploadDir.getAbsolutePath(), created ? "成功" : "失败");
            }
            
            // 创建图片上传目录
            File imagesDir = new File(uploadPath + "/images");
            if (!imagesDir.exists()) {
                boolean created = imagesDir.mkdirs();
                log.info("创建图片上传目录: {}, 结果: {}", imagesDir.getAbsolutePath(), created ? "成功" : "失败");
            }
            
            // 创建模型上传目录
            File modelsDir = new File(uploadPath + "/models");
            if (!modelsDir.exists()) {
                boolean created = modelsDir.mkdirs();
                log.info("创建模型上传目录: {}, 结果: {}", modelsDir.getAbsolutePath(), created ? "成功" : "失败");
            }
            
            // 创建文件上传目录
            File filesDir = new File(uploadPath + "/files");
            if (!filesDir.exists()) {
                boolean created = filesDir.mkdirs();
                log.info("创建文件上传目录: {}, 结果: {}", filesDir.getAbsolutePath(), created ? "成功" : "失败");
            }
            
            log.info("上传目录初始化完成，主目录为: {}", mainUploadDir.getAbsolutePath());
        };
    }
} 