package com.example.secondhandcar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.entity.Order;
import com.example.secondhandcar.vo.OrderVO;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId 用户ID
     * @param carId  车辆ID
     * @return 订单信息
     */
    Order createOrder(Long userId, Long carId);

    /**
     * 支付订单
     *
     * @param id            订单ID
     * @param paymentMethod 支付方式
     * @return 是否成功
     */
    boolean payOrder(Long id, String paymentMethod);

    /**
     * 取消订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean cancelOrder(Long id);

    /**
     * 完成订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean completeOrder(Long id);

    /**
     * 获取订单详情
     *
     * @param id 订单ID
     * @return 订单详情
     */
    OrderVO getOrderDetail(Long id);

    /**
     * 分页查询用户订单
     *
     * @param page   分页参数
     * @param userId 用户ID
     * @param status 订单状态
     * @return 分页结果
     */
    IPage<OrderVO> pageUserOrders(Page<OrderVO> page, Long userId, String status);

    /**
     * 分页查询所有订单
     *
     * @param page   分页参数
     * @param userId 用户ID
     * @param carId  车辆ID
     * @param status 订单状态
     * @return 分页结果
     */
    IPage<OrderVO> pageOrders(Page<OrderVO> page, Long userId, Long carId, String status);
} 