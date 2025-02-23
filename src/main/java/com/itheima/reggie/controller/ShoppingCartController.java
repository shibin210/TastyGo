package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 添加菜品/套餐到购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据：{}", shoppingCart);

        // 1. 获取当前用户 ID
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 2. 查询购物车中是否已有相同菜品或套餐
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 使用 list() 查询，避免 getOne() 的 MyBatis-Plus 异常
        List<ShoppingCart> cartItems = shoppingCartService.list(queryWrapper);
        ShoppingCart existingCartItem = cartItems.isEmpty() ? null : cartItems.get(0);

        if (existingCartItem != null) {
            // 3. 如果已存在，数量 +1
            existingCartItem.setNumber(existingCartItem.getNumber() + 1);
            shoppingCartService.updateById(existingCartItem);
        } else {
            // 4. 如果不存在，新增，确保 number 不为 null
            shoppingCart.setNumber(shoppingCart.getNumber() == null ? 1 : shoppingCart.getNumber());
            shoppingCartService.save(shoppingCart);
            existingCartItem = shoppingCart; // 让 existingCartItem 指向新对象，确保返回正确的数据
        }

        // 5. 返回最新的购物车数据，确保前端更新 UI
        return R.success(existingCartItem);
    }




    /**
     * 添加菜品/套餐到购物车
     * @param shoppingCart
     * @return
     *//*
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据：{}", shoppingCart);

        //1. 获取当前用户 ID
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 2. 查询购物车中是否已有相同菜品或套餐
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (shoppingCart.getDishId() != null) {
            // 添加菜品
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }else {
            // 添加套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        List<ShoppingCart> existingCartItem = shoppingCartService.list(queryWrapper);

        if (existingCartItem != null) {
            // 3. 如果已存在，数量 +1
            existingCartItem.setNumber(existingCartItem.getNumber() + 1);
            shoppingCartService.updateById(existingCartItem);
        }else {
            // 4. 如果不存在，新增，数量设为 1
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
        }

        return R.success(shoppingCart);
    }*/

    /**
     * 查询购物车数据
     * @param shoppingCart
     * @return
     */
    @GetMapping("/api/list")
    public R<List<ShoppingCart>> list(ShoppingCart shoppingCart){
        log.info("购物车数据：{}", shoppingCart);

        // 1. 获取当前用户 ID
        Long currentId = BaseContext.getCurrentId();

        // 2. 构造查询条件，查询当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        // 3. 执行查询
        List<ShoppingCart> cartList = shoppingCartService.list(queryWrapper);

        // ✅ 额外检查是否 number 为 null
        for (ShoppingCart cart : cartList) {
            if (cart.getNumber() == null) {
                cart.setNumber(0);  // 确保不会出现 null
            }
        }

        // 4. 返回数据
        return R.success(cartList);
    }

    @DeleteMapping("/clean")
    public R<String> clean(ShoppingCart shoppingCart){
        log.info("购物车数据：{}", shoppingCart);

        // 1. 获取当前用户 ID
        Long currentId = BaseContext.getCurrentId();

        // 2. 构造查询条件，查询当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        // 3. 执行查询
        shoppingCartService.remove(queryWrapper);

        // 4. 返回数据
        return R.success("购物车已清空");
    }

    /**
     * 根据菜品/套餐id减少数量
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        // 1. 获取当前用户 ID
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 2. 查询购物车中是否已有相同菜品或套餐
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 使用 list() 查询，避免 getOne() 的 MyBatis-Plus 异常
        List<ShoppingCart> cartItems = shoppingCartService.list(queryWrapper);
        ShoppingCart existingCartItem = cartItems.isEmpty() ? null : cartItems.get(0);

        if (existingCartItem != null) {
            // 3. 如果已存在，数量 -1
            existingCartItem.setNumber(existingCartItem.getNumber() - 1);
            shoppingCartService.updateById(existingCartItem);
        }

        // 5. 返回最新的购物车数据，确保前端更新 UI
        return R.success(existingCartItem);
    }
}
