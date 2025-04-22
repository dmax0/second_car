package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.SystemLog;
import com.example.secondhandcar.service.SystemLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 系统日志Controller
 */
@Tag(name = "系统日志管理", description = "系统日志相关接口")
@RestController
@RequestMapping("/system-log")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    @Operation(summary = "添加系统日志", description = "添加系统日志接口")
    @PostMapping
    public Result<SystemLog> add(@RequestBody SystemLog systemLog) {
        SystemLog result = systemLogService.add(systemLog);
        return Result.success(result);
    }

    @Operation(summary = "删除系统日志", description = "删除系统日志接口")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = systemLogService.delete(id);
        return Result.success(result);
    }

    @Operation(summary = "清空系统日志", description = "清空系统日志接口")
    @DeleteMapping("/clear")
    public Result<Boolean> clear() {
        boolean result = systemLogService.clear();
        return Result.success(result);
    }

    @Operation(summary = "获取系统日志详情", description = "获取系统日志详情接口")
    @GetMapping("/{id}")
    public Result<SystemLog> getById(@PathVariable Long id) {
        SystemLog systemLog = systemLogService.getById(id);
        return Result.success(systemLog);
    }

    @Operation(summary = "分页查询系统日志", description = "分页查询系统日志接口")
    @GetMapping("/page")
    public Result<IPage<SystemLog>> page(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID") Long userId,
            @Parameter(description = "操作类型") String actionType,
            @Parameter(description = "状态") String status,
            @Parameter(description = "开始时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<SystemLog> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<SystemLog> result = systemLogService.page(page, userId, actionType, status, startTime, endTime);
        return Result.success(result);
    }
} 