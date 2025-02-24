package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Orders Management Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<Orders> submitOrder(@RequestBody Orders order) {
        Orders createdOrder = ordersService.createOrder(order);
        return R.success(createdOrder);
    }

    @GetMapping("/{id}")
    public R<Orders> getOrderById(@PathVariable Long id) {
        Optional<Orders> order = ordersService.getOrderById(id);
        return order.map(R::success).orElseGet(() -> R.error("Order not found"));
    }

    @GetMapping("/{orderId}/details")
    public R<List<OrderDetail>> getOrderDetails(@PathVariable Long orderId) {
        return R.success(orderDetailService.getOrderDetailsByOrderId(orderId));
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page     The page number (starting from 1 on the frontend, but adjusted to start from 0 internally).
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of categories.
     */
    @GetMapping("/page")
    public R<Map<String, Object>> getCategoryPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // Call the service layer to retrieve paginated data
        Map<String, Object> pageInfo = ordersService.getOrderPage(page, pageSize);
        return R.success(pageInfo);
    }

}
