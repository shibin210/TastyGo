/*
package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.repository.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;


    */
/**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     *//*

    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId(); //菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
 */
/*       flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
*//*

        flavors.forEach(item -> item.setDishId(dishId)); // 这个代码更简洁，而且不需要创建新的 List。

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    */
/**修改菜品一：2. 页面发送ajax请求，请求服务端，根据id查询当前菜品信息，用于菜品信息回显。
     * 根据id查询菜品信息和对应的口味信息,这个实现类的功能是查询菜品表以及口味表，并把这两张表的数据封装到DishDto里面并返回给调用者-控制层。
     * 方法设计思路：
     *      1. 先查询主表信息-菜品表
     *      2. 然后根据返回的主表对象，通过主表和子表的关联字段来查询子表数据
     *      3. 最后把以上所查询到的，主表和子表数据赋值给DishDto 对象，这里主表使用springboot自带的拷贝功能，而子表则使用mybatisPlus的赋值方法。
     *      4. 最后把封装好的 DishDto 返回给这个方法。
     * @param id
     * @return
     *//*

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);
        //创建一个sishDto 用来最终返回给这个方法
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

        //把根据id查询到的口味数据赋值为dishDto.
        dishDto.setFlavors(dishFlavorList);

        return dishDto;
    }

    */
/**修改菜品二：4. 点击保存按钮，页面发送ajax请求，将修改后的菜品相关数据以json形式提交到服务器。
     * 更新菜品信息，同时更新对应的口味信息
     * @param dishDto
     *//*

    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应的口味数据--dish_flavor表的delete操作
        //这里发的SQL语句为：delete from dish_flavor where dish_id = ?;
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        //这里需要构建一个条件，传给删除方法。根据主表和子表关联的id。也就是要删除子表，必须根据前端传来的主表id来执行。
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);

        //添加当前提交过来的口味数据--dish_flavor表的insert操作
        //dishDto.getFlavors() 是从前端传过来的 dishDto 对象中获取口味数据，但是并没有 dish_id，而口味表中有 dish_id 这个字段，
        // 所以 这里获取的 flavors 对象值包含数据库中的 name,value 这两个字段，而没有 dish_id.所以必须手动设置dish_id.
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.forEach((item) -> item.setDishId(dishDto.getId()));

        //这里操作子表-口味表，保存口味数据，因为口味数据为一个数组，所以需要调用 saveBatch() 方法来插入一组数据。
        dishFlavorService.saveBatch(flavors);
    }

    */
/**
     * 菜品状态批量起售停售
     * @param ids
     * @param status
     * @return
     *//*

    @Override
    public boolean updateStatusBatch(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null){
            return false;
        }
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Dish::getId, ids).set(Dish::getStatus, status);
        return this.update(updateWrapper);
    }
}



















*/
