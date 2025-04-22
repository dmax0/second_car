package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 汽车资讯分类实体类
 */
@Data
@TableName("qichezixunfenlei")
public class CarNewsCategory {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 分类名称
     */
    private String typename;
    
    /**
     * 创建时间
     */
    private LocalDateTime addtime;
} 