package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.CarNewsCategory;
import com.example.secondhandcar.mapper.CarNewsCategoryMapper;
import com.example.secondhandcar.service.CarNewsCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 汽车资讯分类服务实现类
 */
@Service
public class CarNewsCategoryServiceImpl extends ServiceImpl<CarNewsCategoryMapper, CarNewsCategory> implements CarNewsCategoryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarNewsCategory add(CarNewsCategory carNewsCategory) {
        carNewsCategory.setAddtime(LocalDateTime.now());
        save(carNewsCategory);
        return carNewsCategory;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(CarNewsCategory carNewsCategory) {
        return updateById(carNewsCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public CarNewsCategory getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<CarNewsCategory> page(Page<CarNewsCategory> page, String typename) {
        LambdaQueryWrapper<CarNewsCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (typename != null && !typename.isEmpty()) {
            queryWrapper.like(CarNewsCategory::getTypename, typename);
        }
        queryWrapper.orderByDesc(CarNewsCategory::getAddtime);
        return page(page, queryWrapper);
    }

    @Override
    public List<CarNewsCategory> listAll() {
        LambdaQueryWrapper<CarNewsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CarNewsCategory::getAddtime);
        return list(queryWrapper);
    }
} 