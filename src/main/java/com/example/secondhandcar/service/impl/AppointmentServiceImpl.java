package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Appointment;
import com.example.secondhandcar.mapper.AppointmentMapper;
import com.example.secondhandcar.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 预约服务实现类
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appointment create(Appointment appointment) {
        // 设置初始状态为待确认
        appointment.setStatus("待确认");
        appointment.setCreateTime(LocalDateTime.now());
        appointment.setUpdateTime(LocalDateTime.now());
        save(appointment);
        return appointment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(Long id) {
        Appointment appointment = getById(id);
        if (appointment == null) {
            return false;
        }
        appointment.setStatus("已确认");
        appointment.setUpdateTime(LocalDateTime.now());
        return updateById(appointment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Long id) {
        Appointment appointment = getById(id);
        if (appointment == null) {
            return false;
        }
        appointment.setStatus("已取消");
        appointment.setUpdateTime(LocalDateTime.now());
        return updateById(appointment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean complete(Long id) {
        Appointment appointment = getById(id);
        if (appointment == null) {
            return false;
        }
        appointment.setStatus("已完成");
        appointment.setUpdateTime(LocalDateTime.now());
        return updateById(appointment);
    }
    
    @Override
    public Appointment getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<Appointment> pageUserAppointments(Page<Appointment> page, Long userId, String status) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Appointment::getStatus, status);
        }
        queryWrapper.orderByDesc(Appointment::getCreateTime);
        return page(page, queryWrapper);
    }

    @Override
    public IPage<Appointment> pageAppointments(Page<Appointment> page, Long userId, Long carId, String status, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            queryWrapper.eq(Appointment::getUserId, userId);
        }
        if (carId != null) {
            queryWrapper.eq(Appointment::getCarId, carId);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Appointment::getStatus, status);
        }
        if (startTime != null) {
            queryWrapper.ge(Appointment::getAppointmentTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(Appointment::getAppointmentTime, endTime);
        }
        queryWrapper.orderByDesc(Appointment::getCreateTime);
        return page(page, queryWrapper);
    }

    @Override
    public List<Appointment> listCarAppointmentsByDate(Long carId, LocalDateTime date) {
        // 获取当天的开始和结束时间
        LocalDate localDate = date.toLocalDate();
        LocalDateTime startOfDay = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(localDate, LocalTime.MAX);
        
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getCarId, carId);
        queryWrapper.ge(Appointment::getAppointmentTime, startOfDay);
        queryWrapper.le(Appointment::getAppointmentTime, endOfDay);
        queryWrapper.ne(Appointment::getStatus, "已取消");
        queryWrapper.orderByAsc(Appointment::getAppointmentTime);
        
        return list(queryWrapper);
    }
} 