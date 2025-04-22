package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.CarNewsCategory;

import java.util.List;

/**
 * 汽车资讯分类服务接口
 */
public interface CarNewsCategoryService {

    /**
     * 添加汽车资讯分类
     *
     * @param carNewsCategory 汽车资讯分类信息
     * @return 汽车资讯分类信息
     */
    CarNewsCategory add(CarNewsCategory carNewsCategory);

    /**
     * 修改汽车资讯分类
     *
     * @param carNewsCategory 汽车资讯分类信息
     * @return 是否成功
     */
    boolean update(CarNewsCategory carNewsCategory);

    /**
     * 删除汽车资讯分类
     *
     * @param id 汽车资讯分类ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取汽车资讯分类详情
     *
     * @param id 汽车资讯分类ID
     * @return 汽车资讯分类详情
     */
    CarNewsCategory getById(Long id);

    /**
     * 分页查询汽车资讯分类
     *
     * @param page 分页参数
     * @param typename 分类名称
     * @return 分页结果
     */
    IPage<CarNewsCategory> page(Page<CarNewsCategory> page, String typename);

    /**
     * 查询所有汽车资讯分类
     *
     * @return 汽车资讯分类列表
     */
    List<CarNewsCategory> listAll();
} 