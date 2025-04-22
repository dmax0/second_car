package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 车辆部件实体类
 */
@Data
@TableName("car_parts")
public class CarPart {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 部件名称
     */
    private String name;
    
    /**
     * 部件描述
     */
    private String description;
    
    /**
     * 模型引用ID
     */
    private String modelReference;
    
    /**
     * 父部件ID
     */
    private Long parentId;
    
    /**
     * 是否可标记
     */
    private Boolean isMarkable;
    
    /**
     * 显示顺序
     */
    private Integer displayOrder;
} 