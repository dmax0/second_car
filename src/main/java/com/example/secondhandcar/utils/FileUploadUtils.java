package com.example.secondhandcar.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传工具类
 */
@Slf4j
@Component
public class FileUploadUtils {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的图片类型
     */
    private static final String[] DEFAULT_ALLOWED_IMAGE_EXTENSION = {
            ".bmp", ".gif", ".jpg", ".jpeg", ".png"
    };

    /**
     * 默认上传的文件类型
     */
    private static final String[] DEFAULT_ALLOWED_FILE_EXTENSION = {
            ".bmp", ".gif", ".jpg", ".jpeg", ".png",
            ".doc", ".docx", ".pdf", ".txt", ".xls", ".xlsx",
            ".zip", ".rar", ".gz", ".mp4", ".avi", ".gltf", ".glb"
    };

    /**
     * 上传图片
     */
    public String uploadImage(MultipartFile file) throws IOException {
        return upload(file, uploadPath + "/images", DEFAULT_ALLOWED_IMAGE_EXTENSION);
    }

    /**
     * 上传汽车3D模型
     */
    public String uploadCarModel(MultipartFile file) throws IOException {
        return upload(file, uploadPath + "/models", new String[]{".gltf", ".glb"});
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) throws IOException {
        return upload(file, uploadPath + "/files", DEFAULT_ALLOWED_FILE_EXTENSION);
    }

    /**
     * 上传文件
     */
    private String upload(MultipartFile file, String baseDir, String[] allowedExtension) throws IOException {
        // 检查文件大小
        checkFileSize(file);
        // 检查文件类型
        String extension = getFileExtension(file);
        if (!isAllowedExtension(extension, allowedExtension)) {
            throw new IOException("文件类型不支持，仅支持" + String.join(",", allowedExtension));
        }

        // 创建目录
        String dateDir = DateUtil.format(new Date(), "yyyy/MM/dd");
        File uploadDir = new File(baseDir + "/" + dateDir);
        
        // 确保目录存在，包括所有父目录
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                log.error("创建目录失败: {}", uploadDir.getAbsolutePath());
                // 尝试使用FileUtil创建目录
                FileUtil.mkdir(uploadDir);
                if (!uploadDir.exists()) {
                    throw new IOException("无法创建目录: " + uploadDir.getAbsolutePath());
                }
            }
        }
        
        log.info("上传文件到目录: {}", uploadDir.getAbsolutePath());

        // 使用UUID重命名文件
        String fileName = IdUtil.fastSimpleUUID() + extension;
        File targetFile = new File(uploadDir, fileName);

        // 创建文件的父目录（以防万一）
        File parentDir = targetFile.getParentFile();
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                log.error("创建父目录失败: {}", parentDir.getAbsolutePath());
                // 尝试使用FileUtil创建目录
                FileUtil.mkdir(parentDir);
                if (!parentDir.exists()) {
                    throw new IOException("无法创建父目录: " + parentDir.getAbsolutePath());
                }
            }
        }

        // 保存文件前日志记录
        log.info("准备保存文件到: {}", targetFile.getAbsolutePath());
        
        try {
            // 保存文件
            file.transferTo(targetFile);
            log.info("文件保存成功: {}", targetFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("文件保存失败: {}, 错误: {}", targetFile.getAbsolutePath(), e.getMessage(), e);
            throw e;
        }

        // 返回文件访问路径（相对路径）
        return "/uploads" + baseDir.substring(uploadPath.length()) + "/" + dateDir + "/" + fileName;
    }

    /**
     * 检查文件大小
     */
    private void checkFileSize(MultipartFile file) throws IOException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new IOException("文件大小不能超过" + DEFAULT_MAX_SIZE / 1024 / 1024 + "MB");
        }
    }

    /**
     * 检查是否允许的文件类型
     */
    private boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
} 