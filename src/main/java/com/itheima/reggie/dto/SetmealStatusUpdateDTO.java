package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Setmeal;
import lombok.Data;

import java.util.List;

@Data
public class SetmealStatusUpdateDTO {
    private List<Long> ids; //需要修改的套餐ID
    private Integer status; //套餐状态（1 = 起售，0 = 停售）
}
