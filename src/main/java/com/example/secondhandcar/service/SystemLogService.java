package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.SystemLog;

import java.time.LocalDateTime;

/**
 * 系统日志服务接口
 */
public interface SystemLogService {

    /**
     * 添加系统日志
     *
     * @param systemLog 系统日志信息
     * @return 系统日志信息
     */
    SystemLog add(SystemLog systemLog);

    /**
     * 删除系统日志
     *
     * @param id 系统日志ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 清空系统日志
     *
     * @return 是否成功
     */
    boolean clear();

    /**
     * 获取系统日志详情
     *
     * @param id 系统日志ID
     * @return 系统日志详情
     */
    SystemLog getById(Long id);

    /**
     * 分页查询系统日志
     *
     * @param page        分页参数
     * @param userId      用户ID
     * @param actionType  操作类型
     * @param status      状态
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 分页结果
     */
    IPage<SystemLog> page(Page<SystemLog> page, Long userId, String actionType, String status, LocalDateTime startTime, LocalDateTime endTime);
} 