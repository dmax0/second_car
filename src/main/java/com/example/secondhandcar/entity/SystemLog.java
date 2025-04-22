package com.example.secondhandcar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统日志实体类
 */
@Data
@TableName("system_logs")
public class SystemLog {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建时间
     */
    private LocalDateTime addtime;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 操作
     */
    private String action;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * IP地址
     */
    private String ipAddress;
    
    /**
     * 时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 操作类型
     */
    private String actionType;
    
    /**
     * 操作对象
     */
    private String targetEntity;
    
    /**
     * 对象ID
     */
    private Long targetId;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 错误信息
     */
    private String errorMessage;
} 