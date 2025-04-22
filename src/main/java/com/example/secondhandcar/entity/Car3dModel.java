package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆3D模型实体类
 */
@Data
@TableName("car_3d_models")
public class Car3dModel {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建时间
     */
    private LocalDateTime addtime;
    
    /**
     * 汽车型号ID
     */
    private Long qichexinghaoid;
    
    /**
     * 模型名称
     */
    private String modelName;
    
    /**
     * 模型文件路径(GLTF/GLB格式)
     */
    private String modelFilePath;
    
    /**
     * 缩略图路径
     */
    private String thumbnailPath;
    
    /**
     * 模型版本
     */
    private String modelVersion;
    
    /**
     * 多边形数量
     */
    private Integer polygonCount;
    
    /**
     * 纹理质量(low/medium/high)
     */
    private String textureQuality;
    
    /**
     * 是否包含内饰模型
     */
    private Boolean hasInterior;
    
    /**
     * 是否包含动画
     */
    private Boolean hasAnimations;
    
    /**
     * 模型比例因子
     */
    private Float modelScale;
    
    /**
     * 模型格式(glb/gltf)
     */
    private String modelFormat;
    
    /**
     * 默认位置
     */
    private String defaultPosition;
    
    /**
     * 默认旋转
     */
    private String defaultRotation;
    
    /**
     * 是否VR优化
     */
    private Boolean vrOptimized;
    
    /**
     * 移动版模型路径(简化)
     */
    private String mobileVersionPath;
    
    /**
     * 是否支持WebXR
     */
    private Boolean supportWebxr;
} 