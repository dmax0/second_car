package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 配置实体类
 */
@Data
@TableName("peizhi")
public class Config {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 配置参数名称
     */
    private String name;
    
    /**
     * 配置参数值
     */
    private String value;
    
    /**
     * url
     */
    private String url;
} 