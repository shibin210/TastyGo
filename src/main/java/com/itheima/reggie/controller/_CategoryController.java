/*
package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity._Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*/
/**
 * 分类管理
 *//*

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    */
/**
     * 新增分类
     * @param category
     * @return
     *//*

    @PostMapping
    public R<String> save(@RequestBody _Category category){
        log.info("category: {}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    */
/**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     *//*

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        //分页构造器
        Page<_Category> pageInfo = new Page<>();
        //条件构造器
        LambdaQueryWrapper<_Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据 sort 进行排序
        queryWrapper.orderByAsc(_Category::getSort);

        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    */
/**
     * 根据id删除分类
     * @param id
     * @return
     *//*

    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);

       // categoryService.removeById(id);
        //需要实现自定义的 删除方法。
        categoryService.remove(id);

        return R.success("分类信息删除成功");
    }

    */
/**
     * 根据id修改分类信息
     * @param category
     * @return
     *//*

    @PutMapping
    public R<String> update(@RequestBody _Category category){
        log.info("修改分类信息：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    */
/**
     * 根据条件查询分类数据，并回显到下拉框
     * @param category
     * @return
     *//*

    @GetMapping("/list")
    public R<List<_Category>> list(_Category category){
        //条件构造器
        LambdaQueryWrapper<_Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        categoryLambdaQueryWrapper.eq(category.getType() != null, _Category::getType, category.getType());
        //添加排序条件
        categoryLambdaQueryWrapper.orderByAsc(_Category::getSort).orderByDesc(_Category::getUpdateTime);

        List<_Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }
}





















*/
