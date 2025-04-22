package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.CarAssessment;
import com.example.secondhandcar.entity.DamageMarker;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.CarAssessmentMapper;
import com.example.secondhandcar.mapper.DamageMarkerMapper;
import com.example.secondhandcar.service.DamageMarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 损伤标记服务实现
 */
@Service
public class DamageMarkerServiceImpl extends ServiceImpl<DamageMarkerMapper, DamageMarker> implements DamageMarkerService {

    @Autowired
    private DamageMarkerMapper damageMarkerMapper;

    @Autowired
    private CarAssessmentMapper carAssessmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DamageMarker add(DamageMarker damageMarker) {
        // 检查评估记录是否存在
        CarAssessment carAssessment = carAssessmentMapper.selectById(damageMarker.getAssessmentId());
        if (carAssessment == null) {
            throw new ServiceException("评估记录不存在");
        }

        // 设置初始值
        damageMarker.setAddtime(new Date());
        if (damageMarker.getSeverityLevel() == null) {
            damageMarker.setSeverityLevel(3);
        }
        if (damageMarker.getMarkerColor() == null) {
            damageMarker.setMarkerColor("#FFFF00");
        }
        if (damageMarker.getMarkerSize() == null) {
            damageMarker.setMarkerSize(1.0f);
        }

        // 保存损伤标记
        damageMarkerMapper.insert(damageMarker);

        // 更新评估记录的损伤标记数量
        carAssessment.setDamageMarkerCount(carAssessment.getDamageMarkerCount() + 1);
        carAssessmentMapper.updateById(carAssessment);

        return damageMarker;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // 查询损伤标记
        DamageMarker damageMarker = damageMarkerMapper.selectById(id);
        if (damageMarker == null) {
            throw new ServiceException("损伤标记不存在");
        }

        // 删除损伤标记
        int result = damageMarkerMapper.deleteById(id);

        // 更新评估记录的损伤标记数量
        if (result > 0) {
            CarAssessment carAssessment = carAssessmentMapper.selectById(damageMarker.getAssessmentId());
            if (carAssessment != null) {
                carAssessment.setDamageMarkerCount(carAssessment.getDamageMarkerCount() - 1);
                carAssessmentMapper.updateById(carAssessment);
            }
        }

        return result > 0;
    }

    @Override
    public List<DamageMarker> listByAssessmentId(Long assessmentId) {
        LambdaQueryWrapper<DamageMarker> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DamageMarker::getAssessmentId, assessmentId);
        queryWrapper.orderByAsc(DamageMarker::getAddtime);
        return damageMarkerMapper.selectList(queryWrapper);
    }
} 