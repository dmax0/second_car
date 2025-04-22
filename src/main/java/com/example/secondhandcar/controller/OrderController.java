package com.example.secondhandcar.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.secondhandcar.common.PageRequest;
import com.example.secondhandcar.common.Result;
import com.example.secondhandcar.entity.Order;
import com.example.secondhandcar.service.OrderService;
import com.example.secondhandcar.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理Controller
 */
@Tag(name = "订单管理", description = "订单相关接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "创建订单", description = "创建订单接口")
    @PostMapping("/create")
    public Result<Order> createOrder(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "车辆ID", required = true) @RequestParam Long carId
    ) {
        Order order = orderService.createOrder(userId, carId);
        return Result.success(order);
    }

    @Operation(summary = "支付订单", description = "支付订单接口")
    @PostMapping("/pay/{id}")
    public Result<Boolean> payOrder(
            @PathVariable Long id,
            @Parameter(description = "支付方式", required = true) @RequestParam String paymentMethod
    ) {
        boolean result = orderService.payOrder(id, paymentMethod);
        return Result.success(result);
    }

    @Operation(summary = "取消订单", description = "取消订单接口")
    @PostMapping("/cancel/{id}")
    public Result<Boolean> cancelOrder(@PathVariable Long id) {
        boolean result = orderService.cancelOrder(id);
        return Result.success(result);
    }

    @Operation(summary = "完成订单", description = "完成订单接口")
    @PostMapping("/complete/{id}")
    public Result<Boolean> completeOrder(@PathVariable Long id) {
        boolean result = orderService.completeOrder(id);
        return Result.success(result);
    }

    @Operation(summary = "获取订单详情", description = "获取订单详情接口")
    @GetMapping("/{id}")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        OrderVO orderVO = orderService.getOrderDetail(id);
        return Result.success(orderVO);
    }

    @Operation(summary = "分页查询用户订单", description = "分页查询用户订单接口")
    @GetMapping("/user/page")
    public Result<IPage<OrderVO>> pageUserOrders(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "订单状态") String status
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<OrderVO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<OrderVO> result = orderService.pageUserOrders(page, userId, status);
        return Result.success(result);
    }

    @Operation(summary = "分页查询所有订单", description = "分页查询所有订单接口")
    @GetMapping("/page")
    public Result<IPage<OrderVO>> pageOrders(
            @Parameter(description = "页码") Integer current,
            @Parameter(description = "每页条数") Integer size,
            @Parameter(description = "用户ID") Long userId,
            @Parameter(description = "车辆ID") Long carId,
            @Parameter(description = "订单状态") String status
    ) {
        PageRequest pageRequest = new PageRequest();
        if (current != null) {
            pageRequest.setCurrent(current);
        }
        if (size != null) {
            pageRequest.setSize(size);
        }

        Page<OrderVO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<OrderVO> result = orderService.pageOrders(page, userId, carId, status);
        return Result.success(result);
    }
} 