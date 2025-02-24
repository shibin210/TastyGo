/*
package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.repository.OrdersMapper;
import com.itheima.reggie.service.AddressBookService;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrdersService;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private AddressBookService addressBookService;


    @Override
    @Transactional
    public void createOrder(Orders order) {
        // 获取当前用户 ID，get current user ID
        Long userId = BaseContext.getCurrentId();

        // 查询购物车中的商品
        List<ShoppingCart> cartItems = shoppingCartService.list(
                new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, userId)
        );

        // 如果购物车为空，抛出异常
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CustomException("购物车为空，无法提交订单");
        }

        // 查询地址信息
        AddressBook address = addressBookService.getById(order.getAddressBookId());
        if (address == null) {
            throw new CustomException("收货地址不存在");
        }

        // 生成订单 ID
        Long orderId = IdWorker.getId();

        // 计算总金额
        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getAmount().multiply(new BigDecimal(item.getNumber())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 组装订单数据
        order.setId(orderId);
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(null); // 这里显式设置 NULL，避免 SQL 报错
        order.setStatus(1); // 1-待支付
        order.setAmount(totalAmount);
        order.setPhone(address.getPhone());
        order.setAddress(address.getDetail());
        order.setConsignee(address.getConsignee());

        // 插入订单表
        this.save(order);


    }

}
*/
