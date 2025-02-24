package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.repository.OrderDetailRepository;
import com.itheima.reggie.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Order Detail Service Implementation (Hibernate Version)
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
