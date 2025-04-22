package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.CarPart;
import com.example.secondhandcar.mapper.CarPartMapper;
import com.example.secondhandcar.service.CarPartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆部件服务实现类
 */
@Service
public class CarPartServiceImpl extends ServiceImpl<CarPartMapper, CarPart> implements CarPartService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarPart add(CarPart carPart) {
        save(carPart);
        return carPart;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(CarPart carPart) {
        return updateById(carPart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public CarPart getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<CarPart> page(Page<CarPart> page, String name, Boolean isMarkable) {
        LambdaQueryWrapper<CarPart> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(CarPart::getName, name);
        }
        if (isMarkable != null) {
            queryWrapper.eq(CarPart::getIsMarkable, isMarkable);
        }
        queryWrapper.orderByAsc(CarPart::getDisplayOrder);
        return page(page, queryWrapper);
    }

    @Override
    public List<CarPart> listTopLevelParts() {
        LambdaQueryWrapper<CarPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(CarPart::getParentId)
                .or()
                .eq(CarPart::getParentId, 0L);
        queryWrapper.orderByAsc(CarPart::getDisplayOrder);
        return list(queryWrapper);
    }

    @Override
    public List<CarPart> listChildParts(Long parentId) {
        LambdaQueryWrapper<CarPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarPart::getParentId, parentId);
        queryWrapper.orderByAsc(CarPart::getDisplayOrder);
        return list(queryWrapper);
    }
} 