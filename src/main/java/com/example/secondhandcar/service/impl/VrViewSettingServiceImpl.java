package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Car3dModel;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.VrViewSetting;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.Car3dModelMapper;
import com.example.secondhandcar.mapper.ErshoucheMapper;
import com.example.secondhandcar.mapper.VrViewSettingMapper;
import com.example.secondhandcar.service.VrViewSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * VR视图设置服务实现
 */
@Service
public class VrViewSettingServiceImpl extends ServiceImpl<VrViewSettingMapper, VrViewSetting> implements VrViewSettingService {

    @Autowired
    private VrViewSettingMapper vrViewSettingMapper;
    
    @Autowired
    private ErshoucheMapper ershoucheMapper;
    
    @Autowired
    private Car3dModelMapper car3dModelMapper;

    @Override
    public VrViewSetting getViewSettings(Long carId) {
        LambdaQueryWrapper<VrViewSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VrViewSetting::getCarId, carId);
        VrViewSetting vrViewSetting = vrViewSettingMapper.selectOne(queryWrapper);
        
        if (vrViewSetting == null) {
            // 查找车辆信息
            Ershouche ershouche = ershoucheMapper.selectById(carId);
            if (ershouche == null) {
                throw new ServiceException("车辆不存在");
            }
            
            // 查找车辆对应的3D模型ID
            Long modelId = ershouche.getModelId();
            if (modelId == null) {
                // 如果车辆没有设置模型ID，查找车型对应的默认模型
                LambdaQueryWrapper<Car3dModel> modelQueryWrapper = new LambdaQueryWrapper<>();
                modelQueryWrapper.eq(Car3dModel::getQichexinghaoid, ershouche.getQichexinghaoid());
                Car3dModel car3dModel = car3dModelMapper.selectOne(modelQueryWrapper);
                
                if (car3dModel == null) {
                    throw new ServiceException("找不到车辆对应的3D模型，请先设置车辆的3D模型");
                }
                
                modelId = car3dModel.getId();
                
                // 更新车辆的模型ID
                ershouche.setModelId(modelId);
                ershoucheMapper.updateById(ershouche);
            }
            
            // 返回默认设置
            vrViewSetting = new VrViewSetting();
            vrViewSetting.setCarId(carId);
            vrViewSetting.setModelId(modelId); // 设置modelId
            vrViewSetting.setAddtime(new Date());
            vrViewSetting.setRecognitionCode(UUID.randomUUID().toString().substring(0, 8));
            vrViewSetting.setCameraPosition("0,5,10");
            vrViewSetting.setCameraTarget("0,0,0");
            vrViewSetting.setAmbientLightColor("#FFFFFF");
            vrViewSetting.setAmbientLightIntensity(0.5f);
            vrViewSetting.setDirectionalLightColor("#FFFFFF");
            vrViewSetting.setDirectionalLightIntensity(0.8f);
            vrViewSetting.setDirectionalLightPosition("10,10,10");
            vrViewSetting.setShadowEnabled(true);
            vrViewSetting.setEnvironmentPreset("showroom");
            vrViewSetting.setViewMode("exterior");
            vrViewSetting.setAutoRotateEnabled(false);
            vrViewSetting.setAutoRotateSpeed(0.5f);
            vrViewSetting.setZoomMin(2.0f);
            vrViewSetting.setZoomMax(10.0f);
            vrViewSetting.setWebxrEnabled(true);
            vrViewSetting.setMobileControlsEnabled(true);
            vrViewSetting.setMarkerLimit(30);
            
            vrViewSettingMapper.insert(vrViewSetting);
        }
        
        return vrViewSetting;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateViewSettings(VrViewSetting vrViewSetting) {
        // 检查是否存在
        LambdaQueryWrapper<VrViewSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VrViewSetting::getCarId, vrViewSetting.getCarId());
        VrViewSetting existSetting = vrViewSettingMapper.selectOne(queryWrapper);
        
        // 确保modelId有值
        if (vrViewSetting.getModelId() == null) {
            // 获取车辆信息
            Ershouche ershouche = ershoucheMapper.selectById(vrViewSetting.getCarId());
            if (ershouche == null) {
                throw new ServiceException("车辆不存在");
            }
            
            // 获取车辆模型ID
            Long modelId = ershouche.getModelId();
            if (modelId == null) {
                // 查找车型对应的默认模型
                LambdaQueryWrapper<Car3dModel> modelQueryWrapper = new LambdaQueryWrapper<>();
                modelQueryWrapper.eq(Car3dModel::getQichexinghaoid, ershouche.getQichexinghaoid());
                Car3dModel car3dModel = car3dModelMapper.selectOne(modelQueryWrapper);
                
                if (car3dModel == null) {
                    throw new ServiceException("找不到车辆对应的3D模型，请先设置车辆的3D模型");
                }
                
                modelId = car3dModel.getId();
            }
            
            vrViewSetting.setModelId(modelId);
        }
        
        if (existSetting != null) {
            // 更新
            vrViewSetting.setId(existSetting.getId());
            int result = vrViewSettingMapper.updateById(vrViewSetting);
            return result > 0;
        } else {
            // 新增
            vrViewSetting.setAddtime(new Date());
            if (vrViewSetting.getRecognitionCode() == null) {
                vrViewSetting.setRecognitionCode(UUID.randomUUID().toString().substring(0, 8));
            }
            int result = vrViewSettingMapper.insert(vrViewSetting);
            return result > 0;
        }
    }
} 