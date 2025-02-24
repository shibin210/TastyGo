package com.itheima.reggie.service;

import com.itheima.reggie.entity.OrderDetail;
import java.util.List;

/**
 * Order Detail Service Interface (Hibernate Version)
 */
public interface OrderDetailService {
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
