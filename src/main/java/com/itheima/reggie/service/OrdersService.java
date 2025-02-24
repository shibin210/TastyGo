package com.itheima.reggie.service;

import com.itheima.reggie.entity.Orders;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Orders Service Interface (Hibernate Version)
 */
public interface OrdersService {
    Orders createOrder(Orders order);
    Optional<Orders> getOrderById(Long id);
    List<Orders> getOrdersByUserId(Long userId);

    Map<String, Object> getOrderPage(int page, int size);

}
