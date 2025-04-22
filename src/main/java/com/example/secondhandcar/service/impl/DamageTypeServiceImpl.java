package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.DamageType;
import com.example.secondhandcar.mapper.DamageTypeMapper;
import com.example.secondhandcar.service.DamageTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 损伤类型服务实现类
 */
@Service
public class DamageTypeServiceImpl extends ServiceImpl<DamageTypeMapper, DamageType> implements DamageTypeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DamageType add(DamageType damageType) {
        save(damageType);
        return damageType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(DamageType damageType) {
        return updateById(damageType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public DamageType getById(Long id) {
        return super.getById(id);
    }

    @Override
    public IPage<DamageType> page(Page<DamageType> page, String typeName) {
        LambdaQueryWrapper<DamageType> queryWrapper = new LambdaQueryWrapper<>();
        if (typeName != null && !typeName.isEmpty()) {
            queryWrapper.like(DamageType::getTypeName, typeName);
        }
        queryWrapper.orderByAsc(DamageType::getDisplayOrder);
        return page(page, queryWrapper);
    }

    @Override
    public List<DamageType> listAll() {
        LambdaQueryWrapper<DamageType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(DamageType::getDisplayOrder);
        return list(queryWrapper);
    }
} 