package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.Qichexinghao;

import java.util.List;

/**
 * 汽车型号服务接口
 */
public interface QichexinghaoService extends IService<Qichexinghao> {
    
    /**
     * 分页查询汽车型号列表
     *
     * @param page 分页参数
     * @param qichexinghao 型号
     * @param pinpai 品牌
     * @param chexi 车系
     * @return 分页结果
     */
    IPage<Qichexinghao> pageList(Page<Qichexinghao> page, String qichexinghao, String pinpai, String chexi);
    
    /**
     * 添加汽车型号
     *
     * @param qichexinghao 汽车型号信息
     * @return 添加结果
     */
    Qichexinghao add(Qichexinghao qichexinghao);
    
    /**
     * 修改汽车型号
     *
     * @param qichexinghao 汽车型号信息
     * @return 修改结果
     */
    boolean update(Qichexinghao qichexinghao);
    
    /**
     * 删除汽车型号
     *
     * @param id 汽车型号ID
     * @return 删除结果
     */
    boolean delete(Long id);
    
    /**
     * 查询所有品牌列表
     *
     * @return 品牌列表
     */
    List<String> getAllPinpai();
    
    /**
     * 根据品牌查询车系列表
     *
     * @param pinpai 品牌
     * @return 车系列表
     */
    List<String> getChexiByPinpai(String pinpai);
} 