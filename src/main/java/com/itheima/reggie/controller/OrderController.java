package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.OrderDetailDTO;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 历史订单分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page<OrderDetailDTO>> getOrderHistory(@RequestParam int page, @RequestParam int pageSize){

        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return R.error("用户未登录");
        }

        //1. 查询订单分页
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId).orderByDesc(Orders::getOrderTime);
        ordersService.page(ordersPage, queryWrapper);

        //2. 组装 DTO 数据
        List<OrderDetailDTO> dtoList = ordersPage.getRecords().stream().map(
                order -> {
                    //查询订单明细
                    LambdaQueryWrapper<OrderDetail> detailQuery = new LambdaQueryWrapper<>();
                    detailQuery.eq(OrderDetail::getOrderId, order.getId());
                    List<OrderDetail> orderDetails = orderDetailService.list(detailQuery);

                    //组装 DTO
                    return new OrderDetailDTO(orderDetails,order);
                }
        ).collect(Collectors.toList());

        //3. 重新封装分页对象
        Page<OrderDetailDTO> dtoPage = new Page<>();
        dtoPage.setRecords(dtoList);
        return R.success(dtoPage);
    }

    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody Orders order){

        //1. 处理购物车数据，生成订单
        ordersService.createOrder(order);

        return null;
    }
}
