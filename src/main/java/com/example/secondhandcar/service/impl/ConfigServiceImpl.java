package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Config;
import com.example.secondhandcar.mapper.ConfigMapper;
import com.example.secondhandcar.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配置服务实现类
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Config add(Config config) {
        save(config);
        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Config config) {
        return updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public Config getById(Long id) {
        return super.getById(id);
    }

    @Override
    public Config getByName(String name) {
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Config::getName, name);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<Config> page(Page<Config> page, String name) {
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Config::getName, name);
        }
        queryWrapper.orderByAsc(Config::getId);
        return page(page, queryWrapper);
    }

    @Override
    public List<Config> listAll() {
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Config::getId);
        return list(queryWrapper);
    }
}
