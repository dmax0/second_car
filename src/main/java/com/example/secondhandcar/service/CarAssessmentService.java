package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.CarAssessment;

/**
 * 车辆评估服务接口
 */
public interface CarAssessmentService extends IService<CarAssessment> {
    
    /**
     * 分页查询评估记录
     *
     * @param page 分页参数
     * @param carId 车辆ID
     * @param assessorId 评估人ID
     * @param status 状态
     * @return 分页结果
     */
    IPage<CarAssessment> pageList(Page<CarAssessment> page, Long carId, Long assessorId, String status);
    
    /**
     * 创建评估记录
     *
     * @param carAssessment 评估记录
     * @return 创建结果
     */
    CarAssessment add(CarAssessment carAssessment);
    
    /**
     * 更新评估记录
     *
     * @param carAssessment 评估记录
     * @return 更新结果
     */
    boolean update(CarAssessment carAssessment);
    
    /**
     * 计算评估总分
     *
     * @param carAssessment 评估记录
     */
    void calculateTotalScore(CarAssessment carAssessment);
} 