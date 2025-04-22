package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 损伤类型实体类
 */
@Data
@TableName("damage_types")
public class DamageType {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 类型名称
     */
    private String typeName;
    
    /**
     * 类型描述
     */
    private String description;
    
    /**
     * 默认颜色
     */
    private String defaultColor;
    
    /**
     * 严重性影响系数
     */
    private Float severityImpact;
    
    /**
     * 显示顺序
     */
    private Integer displayOrder;
    
    /**
     * 图标路径
     */
    private String iconPath;
} 