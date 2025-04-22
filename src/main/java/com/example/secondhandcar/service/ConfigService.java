package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.Config;

import java.util.List;

/**
 * 配置服务接口
 */
public interface ConfigService {

    /**
     * 添加配置
     *
     * @param config 配置信息
     * @return 配置信息
     */
    Config add(Config config);

    /**
     * 修改配置
     *
     * @param config 配置信息
     * @return 是否成功
     */
    boolean update(Config config);

    /**
     * 删除配置
     *
     * @param id 配置ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取配置详情
     *
     * @param id 配置ID
     * @return 配置详情
     */
    Config getById(Long id);

    /**
     * 根据名称获取配置
     *
     * @param name 配置名称
     * @return 配置详情
     */
    Config getByName(String name);

    /**
     * 分页查询配置
     *
     * @param page 分页参数
     * @param name 配置名称
     * @return 分页结果
     */
    IPage<Config> page(Page<Config> page, String name);

    /**
     * 查询所有配置
     *
     * @return 配置列表
     */
    List<Config> listAll();
} 