package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.DamageType;

import java.util.List;

/**
 * 损伤类型服务接口
 */
public interface DamageTypeService {

    /**
     * 添加损伤类型
     *
     * @param damageType 损伤类型信息
     * @return 损伤类型信息
     */
    DamageType add(DamageType damageType);

    /**
     * 修改损伤类型
     *
     * @param damageType 损伤类型信息
     * @return 是否成功
     */
    boolean update(DamageType damageType);

    /**
     * 删除损伤类型
     *
     * @param id 损伤类型ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 获取损伤类型详情
     *
     * @param id 损伤类型ID
     * @return 损伤类型详情
     */
    DamageType getById(Long id);

    /**
     * 分页查询损伤类型
     *
     * @param page 分页参数
     * @param typeName 类型名称
     * @return 分页结果
     */
    IPage<DamageType> page(Page<DamageType> page, String typeName);

    /**
     * 查询所有损伤类型
     *
     * @return 损伤类型列表
     */
    List<DamageType> listAll();
} 