package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.vo.ErshoucheVO;

import java.math.BigDecimal;

/**
 * 二手车信息服务接口
 */
public interface ErshoucheService extends IService<Ershouche> {

    /**
     * 分页查询二手车列表
     *
     * @param page 分页参数
     * @param pinpai 品牌
     * @param chexi 车系
     * @param nianfenStart 年份开始
     * @param nianfenEnd 年份结束
     * @param priceStart 价格开始
     * @param priceEnd 价格结束
     * @param zhuangtai 状态
     * @return 分页结果
     */
    IPage<ErshoucheVO> pageList(
            Page<ErshoucheVO> page,
            String pinpai,
            String chexi,
            Integer nianfenStart,
            Integer nianfenEnd, 
            Integer priceStart,
            Integer priceEnd,
            String zhuangtai
    );

    /**
     * 根据ID查询二手车详细信息
     *
     * @param id 二手车ID
     * @param userId 当前用户ID（用于判断是否收藏）
     * @return 二手车详细信息
     */
    ErshoucheVO getDetailById(Long id, Long userId);

    /**
     * 添加二手车信息
     *
     * @param ershouche 二手车信息
     * @return 添加结果
     */
    Ershouche add(Ershouche ershouche);

    /**
     * 修改二手车信息
     *
     * @param ershouche 二手车信息
     * @return 修改结果
     */
    boolean update(Ershouche ershouche);

    /**
     * 修改二手车状态
     *
     * @param id 二手车ID
     * @param zhuangtai 状态
     * @return 修改结果
     */
    boolean updateStatus(Long id, String zhuangtai);

    /**
     * 修改评估信息
     *
     * @param id 二手车ID
     * @param pingguStatus 评估状态
     * @param pingguZongfen 评估总分
     * @return 修改结果
     */
    boolean updatePinggu(Long id, String pingguStatus, BigDecimal pingguZongfen);

    /**
     * 收藏二手车
     *
     * @param userId 用户ID
     * @param carId 二手车ID
     * @return 收藏结果
     */
    boolean favorite(Long userId, Long carId);

    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param carId 二手车ID
     * @return 取消结果
     */
    boolean cancelFavorite(Long userId, Long carId);

    /**
     * 查询用户收藏的二手车列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @return 收藏列表
     */
    IPage<ErshoucheVO> pageFavorites(Page<ErshoucheVO> page, Long userId);
} 