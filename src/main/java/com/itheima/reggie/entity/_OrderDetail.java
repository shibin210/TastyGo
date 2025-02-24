/*
package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_detail") // 关联数据库表名
public class OrderDetail {
    
    @TableId
    private Long id; // 订单明细ID
    
    private Long orderId; // 关联订单ID
    private Long dishId; // 关联菜品ID（如果是菜品）
    private Long setmealId; // 关联套餐ID（如果是套餐）
    private String name; // 菜品或套餐名称
    private String image; // 商品图片
    private String dishFlavor; // 口味信息
    private Integer number; // 购买数量
    private BigDecimal amount; // 小计金额（单价 * 数量）
}
*/
