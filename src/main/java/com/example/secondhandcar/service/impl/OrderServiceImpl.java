package com.example.secondhandcar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.secondhandcar.entity.Ershouche;
import com.example.secondhandcar.entity.Order;
import com.example.secondhandcar.entity.Yonghu;
import com.example.secondhandcar.exception.ServiceException;
import com.example.secondhandcar.mapper.ErshoucheMapper;
import com.example.secondhandcar.mapper.OrderMapper;
import com.example.secondhandcar.mapper.YonghuMapper;
import com.example.secondhandcar.service.OrderService;
import com.example.secondhandcar.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private YonghuMapper yonghuMapper;
    
    @Autowired
    private ErshoucheMapper ershoucheMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Long userId, Long carId) {
        // 检查用户是否存在
        Yonghu yonghu = yonghuMapper.selectById(userId);
        if (yonghu == null) {
            throw new ServiceException("用户不存在");
        }
        
        // 检查车辆是否存在
        Ershouche ershouche = ershoucheMapper.selectById(carId);
        if (ershouche == null) {
            throw new ServiceException("车辆不存在");
        }
        
        // 检查车辆是否已售出
        if ("已售出".equals(ershouche.getZhuangtai())) {
            throw new ServiceException("车辆已售出");
        }
        
        // 创建订单
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setCarId(carId);
        order.setAmount(ershouche.getShoujia());
        order.setStatus("待支付");
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        // 保存订单
        save(order);
        
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(Long id, String paymentMethod) {
        Order order = getById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        
        if (!"待支付".equals(order.getStatus())) {
            throw new ServiceException("订单状态不正确");
        }
        
        // 更新订单状态
        order.setStatus("已支付");
        order.setPaymentMethod(paymentMethod);
        order.setPaymentTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        boolean updated = updateById(order);
        
        if (updated) {
            // 更新车辆状态为已售出
            Ershouche ershouche = ershoucheMapper.selectById(order.getCarId());
            if (ershouche != null) {
                ershouche.setZhuangtai("已售出");
                ershoucheMapper.updateById(ershouche);
            }
        }
        
        return updated;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        
        if (!"待支付".equals(order.getStatus())) {
            throw new ServiceException("只能取消待支付订单");
        }
        
        // 更新订单状态
        order.setStatus("已取消");
        order.setUpdateTime(LocalDateTime.now());
        
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        
        if (!"已支付".equals(order.getStatus())) {
            throw new ServiceException("只能完成已支付订单");
        }
        
        // 更新订单状态
        order.setStatus("已完成");
        order.setCompletionTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        return updateById(order);
    }

    @Override
    public OrderVO getOrderDetail(Long id) {
        Order order = getById(id);
        if (order == null) {
            return null;
        }
        
        OrderVO orderVO = OrderVO.fromEntity(order);
        
        // 获取用户信息
        Yonghu yonghu = yonghuMapper.selectById(order.getUserId());
        if (yonghu != null) {
            orderVO.setUserName(yonghu.getYonghuming());
            orderVO.setUserPhone(yonghu.getShoujihao());
        }
        
        // 获取车辆信息
        Ershouche ershouche = ershoucheMapper.selectById(order.getCarId());
        if (ershouche != null) {
            String carName = ershouche.getPinpai() + " " + ershouche.getChexi() + " " + ershouche.getChexing();
            orderVO.setCarName(carName);
            orderVO.setCarImage(ershouche.getTupian());
        }
        
        return orderVO;
    }

    @Override
    public IPage<OrderVO> pageUserOrders(Page<OrderVO> page, Long userId, String status) {
        // 查询用户订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId);
        
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Order::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Order::getCreateTime);
        
        // 分页查询
        Page<Order> orderPage = new Page<>(page.getCurrent(), page.getSize());
        Page<Order> orderResult = page(orderPage, queryWrapper);
        
        // 转换为VO
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderResult.getRecords()) {
            OrderVO orderVO = getOrderDetail(order.getId());
            if (orderVO != null) {
                orderVOList.add(orderVO);
            }
        }
        
        // 构建VO分页结果
        Page<OrderVO> result = new Page<>(orderResult.getCurrent(), orderResult.getSize(), orderResult.getTotal());
        result.setRecords(orderVOList);
        
        return result;
    }

    @Override
    public IPage<OrderVO> pageOrders(Page<OrderVO> page, Long userId, Long carId, String status) {
        // 构建查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        
        if (userId != null) {
            queryWrapper.eq(Order::getUserId, userId);
        }
        
        if (carId != null) {
            queryWrapper.eq(Order::getCarId, carId);
        }
        
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Order::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Order::getCreateTime);
        
        // 分页查询
        Page<Order> orderPage = new Page<>(page.getCurrent(), page.getSize());
        Page<Order> orderResult = page(orderPage, queryWrapper);
        
        // 转换为VO
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderResult.getRecords()) {
            OrderVO orderVO = getOrderDetail(order.getId());
            if (orderVO != null) {
                orderVOList.add(orderVO);
            }
        }
        
        // 构建VO分页结果
        Page<OrderVO> result = new Page<>(orderResult.getCurrent(), orderResult.getSize(), orderResult.getTotal());
        result.setRecords(orderVOList);
        
        return result;
    }
    
    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
} 