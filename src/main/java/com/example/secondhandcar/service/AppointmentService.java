package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约服务接口
 */
public interface AppointmentService {

    /**
     * 创建预约
     *
     * @param appointment 预约信息
     * @return 预约信息
     */
    Appointment create(Appointment appointment);

    /**
     * 确认预约
     *
     * @param id 预约ID
     * @return 是否成功
     */
    boolean confirm(Long id);

    /**
     * 取消预约
     *
     * @param id 预约ID
     * @return 是否成功
     */
    boolean cancel(Long id);

    /**
     * 完成预约
     *
     * @param id 预约ID
     * @return 是否成功
     */
    boolean complete(Long id);

    /**
     * 获取预约详情
     *
     * @param id 预约ID
     * @return 预约详情
     */
    Appointment getById(Long id);

    /**
     * 分页查询用户预约
     *
     * @param page   分页参数
     * @param userId 用户ID
     * @param status 预约状态
     * @return 分页结果
     */
    IPage<Appointment> pageUserAppointments(Page<Appointment> page, Long userId, String status);

    /**
     * 分页查询所有预约
     *
     * @param page      分页参数
     * @param userId    用户ID
     * @param carId     车辆ID
     * @param status    预约状态
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分页结果
     */
    IPage<Appointment> pageAppointments(Page<Appointment> page, Long userId, Long carId, String status, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询车辆当日预约情况
     *
     * @param carId 车辆ID
     * @param date  日期
     * @return 预约列表
     */
    List<Appointment> listCarAppointmentsByDate(Long carId, LocalDateTime date);
} 