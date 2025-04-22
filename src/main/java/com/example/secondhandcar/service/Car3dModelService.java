package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.Car3dModel;

import java.util.List;

/**
 * 车辆3D模型服务接口
 */
public interface Car3dModelService {

    /**
     * 添加车辆3D模型
     *
     * @param car3dModel 车辆3D模型信息
     * @return 车辆3D模型信息
     */
    Car3dModel add(Car3dModel car3dModel);

    /**
     * 修改车辆3D模型
     *
     * @param car3dModel 车辆3D模型信息
     * @return 是否成功
     */
    boolean update(Car3dModel car3dModel);

    /**
     * 删除车辆3D模型
     *
     * @param id 车辆3D模型ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取车辆3D模型详情
     *
     * @param id 车辆3D模型ID
     * @return 车辆3D模型详情
     */
    Car3dModel getById(Long id);

    /**
     * 分页查询车辆3D模型
     *
     * @param page 分页参数
     * @param qichexinghaoid 汽车型号ID
     * @param modelName 模型名称
     * @return 分页结果
     */
    IPage<Car3dModel> page(Page<Car3dModel> page, Long qichexinghaoid, String modelName);

    /**
     * 查询指定汽车型号的所有3D模型
     *
     * @param qichexinghaoid 汽车型号ID
     * @return 3D模型列表
     */
    List<Car3dModel> listByQicheXinghaoId(Long qichexinghaoid);
} 