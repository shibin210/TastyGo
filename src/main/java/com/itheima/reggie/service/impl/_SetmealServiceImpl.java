/*
package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.repository.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithSetmealDish(@RequestBody SetmealDto setmealDto) {
        //保存套餐数据需要操作两张表，首先要保存套餐基本信息，然后再保存套餐和菜品的关系信息。
        //1.保存套餐基本信息到 setmeal 表
        this.save(setmealDto);

        //2. 获取套餐 ID
        String setmealId = setmealDto.getId().toString();
        //3. 获取套餐菜品关系信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        //4. 绑定套餐 ID
        setmealDishes.forEach((item) -> item.setSetmealId(setmealId));

        //4. 批量插入套餐菜品关系数据
        setmealDishService.saveBatch(setmealDishes);
    }

    */
/**
     * 根据id删除分类
     * @param ids
     * @return
     *//*

    @Transactional
    public void removeWithDish(List<Long> ids) {
        */
/*实现步骤
            1. 查询要删除的套餐 ID 是否关联在售的菜品
            2. 通过 setmeal_dish 关联 dish 表，判断是否有 status = 1 的菜品。
            3. 如果存在在售菜品，抛出异常，阻止删除
            4. 如果不存在在售菜品，删除 setmeal 及其关联的 setmeal_dish 记录
        *//*

        //1. 查询要删除的套餐 ID 是否关联在售的菜品
        //select count(*) from setmeal where id in (1,2,3) and status = 1;
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);

        long count = this.count(setmealLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("当前套餐包含在售状态的套餐，无法删除！");
        }

        //2. 先删除套餐
        this.removeByIds(ids);
        this.removeBatchByIds(ids);

        //3. 再删除套餐菜品关系表
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

    @Override
    public boolean updateStatusBatch(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null){
            return false;
        }
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId, ids).set(Setmeal::getStatus, status);
        return this.update(updateWrapper);
    }
}













*/
