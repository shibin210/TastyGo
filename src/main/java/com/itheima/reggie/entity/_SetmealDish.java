/*
package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

*/
/**
 *
 * 套餐跟菜品的关系实体
 *//*

@Data
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1L;
    //套餐菜品关联id
    private Long id;
    //套餐id
    private String setmealId;
    //菜品名称（冗余字段）
    private String name;
    //菜品价格
    private BigDecimal price;
    //菜品id
    private String dishId;
    //份数
    private Integer copies;
    //0 停售， 1 起售
    private Integer sort;


    //删除状态
    private Integer isDeleted;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
*/
