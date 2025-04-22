package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.Shoucang;
import com.example.secondhandcar.vo.ErshoucheVO;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface ShoucangService extends IService<Shoucang> {
    
    /**
     * 添加收藏
     *
     * @param userId 用户ID
     * @param carId 车辆ID
     * @return 添加结果
     */
    boolean add(Long userId, Long carId);
    
    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param carId 车辆ID
     * @return 取消结果
     */
    boolean cancel(Long userId, Long carId);
    
    /**
     * 检查是否已收藏
     *
     * @param userId 用户ID
     * @param carId 车辆ID
     * @return 是否已收藏
     */
    boolean isCollected(Long userId, Long carId);
    
    /**
     * 获取用户收藏的车辆列表
     *
     * @param userId 用户ID
     * @return 车辆列表
     */
    List<ErshoucheVO> getUserFavorites(Long userId);
    
    /**
     * 分页获取用户收藏的车辆列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<ErshoucheVO> pageUserFavorites(Page<ErshoucheVO> page, Long userId);
} 