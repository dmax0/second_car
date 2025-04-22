package com.example.secondhandcar.vo;

import com.example.secondhandcar.entity.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单展示对象
 */
@Data
public class OrderVO {
    
    private Long id;
    
    /**
     * 订单编号
     */
    private String orderNo;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 用户电话
     */
    private String userPhone;
    
    /**
     * 车辆ID
     */
    private Long carId;
    
    /**
     * 车辆名称
     */
    private String carName;
    
    /**
     * 车辆图片
     */
    private String carImage;
    
    /**
     * 订单金额
     */
    private BigDecimal amount;
    
    /**
     * 订单状态：待支付、已支付、已取消、已完成
     */
    private String status;
    
    /**
     * 支付方式：微信、支付宝、银行转账
     */
    private String paymentMethod;
    
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;
    
    /**
     * 交易完成时间
     */
    private LocalDateTime completionTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 从实体类转换
     */
    public static OrderVO fromEntity(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setUserId(order.getUserId());
        vo.setCarId(order.getCarId());
        vo.setAmount(order.getAmount());
        vo.setStatus(order.getStatus());
        vo.setPaymentMethod(order.getPaymentMethod());
        vo.setPaymentTime(order.getPaymentTime());
        vo.setCompletionTime(order.getCompletionTime());
        vo.setCreateTime(order.getCreateTime());
        vo.setUpdateTime(order.getUpdateTime());
        vo.setRemark(order.getRemark());
        
        return vo;
    }
} 