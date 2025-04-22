package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Car3dModel;
import com.example.secondhandcar.mapper.Car3dModelMapper;
import com.example.secondhandcar.service.Car3dModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆3D模型服务实现类
 */
@Service
public class Car3dModelServiceImpl extends ServiceImpl<Car3dModelMapper, Car3dModel> implements Car3dModelService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Car3dModel add(Car3dModel car3dModel) {
        car3dModel.setAddtime(LocalDateTime.now());
        save(car3dModel);
        return car3dModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Car3dModel car3dModel) {
        return updateById(car3dModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public Car3dModel getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<Car3dModel> page(Page<Car3dModel> page, Long qichexinghaoid, String modelName) {
        LambdaQueryWrapper<Car3dModel> queryWrapper = new LambdaQueryWrapper<>();
        if (qichexinghaoid != null) {
            queryWrapper.eq(Car3dModel::getQichexinghaoid, qichexinghaoid);
        }
        if (modelName != null && !modelName.isEmpty()) {
            queryWrapper.like(Car3dModel::getModelName, modelName);
        }
        queryWrapper.orderByDesc(Car3dModel::getAddtime);
        return page(page, queryWrapper);
    }

    @Override
    public List<Car3dModel> listByQicheXinghaoId(Long qichexinghaoid) {
        LambdaQueryWrapper<Car3dModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Car3dModel::getQichexinghaoid, qichexinghaoid);
        queryWrapper.orderByDesc(Car3dModel::getAddtime);
        return list(queryWrapper);
    }
} 