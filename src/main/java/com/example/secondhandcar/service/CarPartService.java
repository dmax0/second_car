package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.CarPart;

import java.util.List;

/**
 * 车辆部件服务接口
 */
public interface CarPartService {

    /**
     * 添加车辆部件
     *
     * @param carPart 车辆部件信息
     * @return 车辆部件信息
     */
    CarPart add(CarPart carPart);

    /**
     * 修改车辆部件
     *
     * @param carPart 车辆部件信息
     * @return 是否成功
     */
    boolean update(CarPart carPart);

    /**
     * 删除车辆部件
     *
     * @param id 车辆部件ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取车辆部件详情
     *
     * @param id 车辆部件ID
     * @return 车辆部件详情
     */
    CarPart getById(Long id);

    /**
     * 分页查询车辆部件
     *
     * @param page 分页参数
     * @param name 部件名称
     * @param isMarkable 是否可标记
     * @return 分页结果
     */
    IPage<CarPart> page(Page<CarPart> page, String name, Boolean isMarkable);

    /**
     * 查询顶级部件列表
     *
     * @return 顶级部件列表
     */
    List<CarPart> listTopLevelParts();

    /**
     * 查询子部件列表
     *
     * @param parentId 父部件ID
     * @return 子部件列表
     */
    List<CarPart> listChildParts(Long parentId);
} 