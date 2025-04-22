package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Appointment;
import com.example.secondhandcar.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约看车Controller
 */
@Tag(name = "预约看车管理", description = "预约看车相关接口")
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "创建预约", description = "创建预约接口")
    @PostMapping
    public Result<Appointment> create(@RequestBody Appointment appointment) {
        Appointment result = appointmentService.create(appointment);
        return Result.success(result);
    }

    @Operation(summary = "确认预约", description = "确认预约接口")
    @PostMapping("/confirm/{id}")
    public Result<Boolean> confirm(@PathVariable Long id) {
        boolean result = appointmentService.confirm(id);
        return Result.success(result);
    }

    @Operation(summary = "取消预约", description = "取消预约接口")
    @PostMapping("/cancel/{id}")
    public Result<Boolean> cancel(@PathVariable Long id) {
        boolean result = appointmentService.cancel(id);
        return Result.success(result);
    }

    @Operation(summary = "完成预约", description = "完成预约接口")
    @PostMapping("/complete/{id}")
    public Result<Boolean> complete(@PathVariable Long id) {
        boolean result = appointmentService.complete(id);
        return Result.success(result);
    }

    @Operation(summary = "获取预约详情", description = "获取预约详情接口")
    @GetMapping("/{id}")
    public Result<Appointment> getById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        return Result.success(appointment);
    }

    @Operation(summary = "分页查询用户预约", description = "分页查询用户预约接口")
    @GetMapping("/user/page")
    public Result<IPage<Appointment>> pageUserAppointments(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "预约状态") String status
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<Appointment> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Appointment> result = appointmentService.pageUserAppointments(page, userId, status);
        return Result.success(result);
    }

    @Operation(summary = "分页查询所有预约", description = "分页查询所有预约接口")
    @GetMapping("/page")
    public Result<IPage<Appointment>> pageAppointments(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID") Long userId,
            @Parameter(description = "车辆ID") Long carId,
            @Parameter(description = "预约状态") String status,
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

        Page<Appointment> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Appointment> result = appointmentService.pageAppointments(page, userId, carId, status, startTime, endTime);
        return Result.success(result);
    }

    @Operation(summary = "查询车辆当日预约情况", description = "查询车辆当日预约情况接口")
    @GetMapping("/car/date")
    public Result<List<Appointment>> listCarAppointmentsByDate(
            @Parameter(description = "车辆ID", required = true) @RequestParam Long carId,
            @Parameter(description = "日期", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime date
    ) {
        List<Appointment> list = appointmentService.listCarAppointmentsByDate(carId, date);
        return Result.success(list);
    }
} 