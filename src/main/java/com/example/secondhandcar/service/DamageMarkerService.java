package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.DamageMarker;

import java.util.List;

/**
 * 损伤标记服务接口
 */
public interface DamageMarkerService extends IService<DamageMarker> {
    
    /**
     * 添加损伤标记
     *
     * @param damageMarker 损伤标记
     * @return 添加结果
     */
    DamageMarker add(DamageMarker damageMarker);
    
    /**
     * 删除损伤标记
     *
     * @param id 损伤标记ID
     * @return 删除结果
     */
    boolean delete(Long id);
    
    /**
     * 查询评估记录的损伤标记列表
     *
     * @param assessmentId 评估记录ID
     * @return 损伤标记列表
     */
    List<DamageMarker> listByAssessmentId(Long assessmentId);
} 