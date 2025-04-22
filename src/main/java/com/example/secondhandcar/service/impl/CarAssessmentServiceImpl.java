package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.CarAssessment;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.CarAssessmentMapper;
import com.example.secondhandcar.service.CarAssessmentService;
import com.example.secondhandcar.service.ErshoucheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 车辆评估服务实现
 */
@Service
public class CarAssessmentServiceImpl extends ServiceImpl<CarAssessmentMapper, CarAssessment> implements CarAssessmentService {

    @Autowired
    private CarAssessmentMapper carAssessmentMapper;

    @Autowired
    private ErshoucheService ershoucheService;

    @Override
    public IPage<CarAssessment> pageList(Page<CarAssessment> page, Long carId, Long assessorId, String status) {
        return carAssessmentMapper.selectAssessmentPage(page, carId, assessorId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarAssessment add(CarAssessment carAssessment) {
        // 检查车辆是否存在
        Ershouche ershouche = ershoucheService.getById(carAssessment.getCarId());
        if (ershouche == null) {
            throw new ServiceException("车辆不存在");
        }

        // 生成评估编号
        if (!StringUtils.hasText(carAssessment.getAssessmentCode())) {
            carAssessment.setAssessmentCode("AS" + System.currentTimeMillis());
        }

        // 设置初始值
        carAssessment.setAddtime(new Date());
        carAssessment.setAssessmentDate(new Date());
        if (carAssessment.getStatus() == null) {
            carAssessment.setStatus("进行中");
        }
        if (carAssessment.getDamageMarkerCount() == null) {
            carAssessment.setDamageMarkerCount(0);
        }
        if (carAssessment.getTotalScore() == null) {
            carAssessment.setTotalScore(BigDecimal.ZERO);
        }
        if (carAssessment.getEstimatedValue() == null) {
            carAssessment.setEstimatedValue(BigDecimal.ZERO);
        }

        carAssessmentMapper.insert(carAssessment);
        return carAssessment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(CarAssessment carAssessment) {
        // 检查是否存在
        CarAssessment existCarAssessment = carAssessmentMapper.selectById(carAssessment.getId());
        if (existCarAssessment == null) {
            throw new ServiceException("评估记录不存在");
        }

        // 计算评估总分
        calculateTotalScore(carAssessment);

        // 更新评估记录
        int result = carAssessmentMapper.updateById(carAssessment);

        // 如果评估完成，更新车辆评估状态
        if ("已完成".equals(carAssessment.getStatus())) {
            ershoucheService.updatePinggu(carAssessment.getCarId(), "已评估", carAssessment.getTotalScore());
        }

        return result > 0;
    }

    @Override
    public void calculateTotalScore(CarAssessment carAssessment) {
        // 各项得分总和
        int totalPoints = 0;
        int itemCount = 0;

        // 外观状态
        if (carAssessment.getExteriorCondition() != null && carAssessment.getExteriorCondition() > 0) {
            totalPoints += carAssessment.getExteriorCondition();
            itemCount++;
        }

        // 内饰状态
        if (carAssessment.getInteriorCondition() != null && carAssessment.getInteriorCondition() > 0) {
            totalPoints += carAssessment.getInteriorCondition();
            itemCount++;
        }

        // 驾驶系统
        if (carAssessment.getDrivingSystem() != null && carAssessment.getDrivingSystem() > 0) {
            totalPoints += carAssessment.getDrivingSystem();
            itemCount++;
        }

        // 发动机状态
        if (carAssessment.getEngine() != null && carAssessment.getEngine() > 0) {
            totalPoints += carAssessment.getEngine();
            itemCount++;
        }

        // 变速箱状态
        if (carAssessment.getTransmission() != null && carAssessment.getTransmission() > 0) {
            totalPoints += carAssessment.getTransmission();
            itemCount++;
        }

        // 底盘状态
        if (carAssessment.getChassis() != null && carAssessment.getChassis() > 0) {
            totalPoints += carAssessment.getChassis();
            itemCount++;
        }

        // 电气系统状态
        if (carAssessment.getElectricalSystem() != null && carAssessment.getElectricalSystem() > 0) {
            totalPoints += carAssessment.getElectricalSystem();
            itemCount++;
        }

        // 其他配置状态
        if (carAssessment.getOtherConfig() != null && carAssessment.getOtherConfig() > 0) {
            totalPoints += carAssessment.getOtherConfig();
            itemCount++;
        }

        // 计算平均分（满分5分）
        if (itemCount > 0) {
            BigDecimal score = new BigDecimal(totalPoints).divide(new BigDecimal(itemCount), 1, RoundingMode.HALF_UP);
            
            // 损伤标记数量对分数的影响
            int damageCount = carAssessment.getDamageMarkerCount() != null ? carAssessment.getDamageMarkerCount() : 0;
            if (damageCount > 0) {
                // 根据损伤数量适当减分，最多减1分
                BigDecimal deduction = BigDecimal.valueOf(Math.min(damageCount * 0.1, 1.0));
                score = score.subtract(deduction);
                if (score.compareTo(BigDecimal.ONE) < 0) {
                    score = BigDecimal.ONE; // 最低分1分
                }
            }
            
            carAssessment.setTotalScore(score);
        }
    }
} 