package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.SystemLog;
import com.example.secondhandcar.mapper.SystemLogMapper;
import com.example.secondhandcar.service.SystemLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 系统日志服务实现类
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemLog add(SystemLog systemLog) {
        if (systemLog.getAddtime() == null) {
            systemLog.setAddtime(LocalDateTime.now());
        }
        if (systemLog.getTimestamp() == null) {
            systemLog.setTimestamp(LocalDateTime.now());
        }
        if (systemLog.getStatus() == null) {
            systemLog.setStatus("success");
        }
        save(systemLog);
        return systemLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean clear() {
        // 清空所有日志记录
        return remove(null);
    }

    @Override
    public SystemLog getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<SystemLog> page(Page<SystemLog> page, Long userId, String actionType, String status, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<SystemLog> queryWrapper = new LambdaQueryWrapper<>();
        
        if (userId != null) {
            queryWrapper.eq(SystemLog::getUserId, userId);
        }
        
        if (actionType != null && !actionType.isEmpty()) {
            queryWrapper.eq(SystemLog::getActionType, actionType);
        }
        
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(SystemLog::getStatus, status);
        }
        
        if (startTime != null) {
            queryWrapper.ge(SystemLog::getTimestamp, startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le(SystemLog::getTimestamp, endTime);
        }
        
        queryWrapper.orderByDesc(SystemLog::getTimestamp);
        
        return page(page, queryWrapper);
    }
} 