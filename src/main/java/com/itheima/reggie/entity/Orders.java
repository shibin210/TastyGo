package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders") // 关联数据库表名
public class Orders {
    
    @TableId
    private Long id; // 订单ID
    
    private String number; // 订单编号
    private Integer status; // 订单状态（1:待支付, 2:已支付, 3:已派送, 4:已完成, 5:已取消）
    private Long userId; // 用户ID
    private Long addressBookId; // 地址ID
    private LocalDateTime orderTime; // 下单时间
    private LocalDateTime checkoutTime; // 结账时间
    private Integer payMethod; // 支付方式（1: 微信, 2: 支付宝）
    private BigDecimal amount; // 订单总金额
    private String remark; // 订单备注
    private String phone; // 用户手机号
    private String address; // 收货地址
    private String userName; // 用户姓名
    private String consignee; // 收货人
}
