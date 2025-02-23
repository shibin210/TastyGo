package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.dto.SetmealStatusUpdateDTO;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@Slf4j
@RestController
@RequestMapping("/api/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息{}", setmealDto);
        setmealService.saveWithSetmealDish(setmealDto);
        return R.success("成功新增套餐");
    }

    //套餐管理的分页开发
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //先创建一个分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);

        //构造一个 DTO 类型的分页构造器
        Page<SetmealDto> setmealDtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        setmealLambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        //添加排序条件
        setmealLambdaQueryWrapper.orderByAsc(Setmeal::getUpdateTime);
        //执行分页查询
        setmealService.page(pageInfo, setmealLambdaQueryWrapper);

        //处理分页中的 records 对象
        //对象拷贝,忽略 records 对象，因为需要 SetmealDishDto 类型，需要手动转换类型。
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map(
                setmeal -> {
                    //填充 setmealDto 对象属性
                    SetmealDto setmealDto = new SetmealDto();
                    //把 Setmeal 的一般属性拷贝到 setmealDto.
                    BeanUtils.copyProperties(setmeal, setmealDto);
                    //先通过 setmeal 对象中获取的 categoryId ,再通过 categoryId 获取 categoryName 用于页面展示
                    Long categoryId = setmeal.getCategoryId();
                    Category category = categoryService.getById(categoryId);
                    if (category != null) {
                        String categoryName = category.getName();
                        setmealDto.setCategoryName(categoryName);
                    }
                    return setmealDto;
                }
        ).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }

    /**
     * 根据id删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids为：{}", ids);

        // categoryService.removeById(id);
        //需要实现自定义的 删除方法。
        setmealService.removeWithDish(ids);

        return R.success("分类信息删除成功");
    }

    /**
     * 套餐状态批量起售停售
     * @param ids
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {

        if (ids == null || ids.isEmpty()) {
            return R.error("ID 不能为空");
        }
        // 调用 Service 方法批量更新状态
        boolean updated = setmealService.updateStatusBatch(ids, status);
        return updated ? R.success("更新成功") : R.error("更新失败");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper
                .eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(setmealList);
    }
}















