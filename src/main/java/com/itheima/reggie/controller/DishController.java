package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        /*
        * 这里的思路：
          先拷贝 pageInfo 到 dishDtoPage，但不拷贝 records（因为 records 泛型不同）。
          手动转换 records，然后重新赋值。
          ✅ 这样做的好处：
          保证泛型安全，不会发生 ClassCastException。
          不会影响 pageInfo 的原始数据，即 pageInfo.getRecords() 仍然是 List<Dish>，而 dishDtoPage.getRecords()
          是 List<DishDto>，它们是两个不同的列表，不会相互影响。
        * */

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        //创建一个 DishDto 类型的 Page 对象,dishDtoPage是最终要返回的对象，所以需要手动构建一个 DishDto 类型的Page.
        // 先把Page 对象的一般属性从Dish 对象拷贝到DishDto 对象中，然后通过dish对象获取category_id,再通过categoryService
        // 服务层来通过id查找category对象，然后通过category对象获取 categoryName,最后把categoryName赋值给dishDto.dishDto
        // 的其他属性也需要从dish对象中拷贝过去---这个代码总体思路是这样。
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //添加过滤条件
        dishLambdaQueryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,dishLambdaQueryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map(
                (item) -> {
                    //填充 dishDto 对象的属性
                    DishDto dishDto = new DishDto();
                    //把dish的一般属性拷贝到dishDto
                    BeanUtils.copyProperties(item, dishDto);
                    //把通过dish对象中获取的categoryName赋值给dishDto
                    //根据id查询分类对象
                    Long categoryId = item.getCategoryId();
                    Category category = categoryService.getById(categoryId);
                    if (category != null) {
                        String categoryName = category.getName();
                        dishDto.setCategoryName(categoryName);
                    }
                    return dishDto;
                }
        ).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /*
    * // 先拷贝所有属性（包括 records，但无效）
BeanUtils.copyProperties(pageInfo, dishDtoPage);

// 重新转换 records 并覆盖
dishDtoPage.setRecords(pageInfo.getRecords().stream().map(item -> {
    DishDto dishDto = new DishDto();
    BeanUtils.copyProperties(item, dishDto);
    dishDto.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
    return dishDto;
}).collect(Collectors.toList()));

    * */

    /**修改菜品一：处理前端的第2步请求. 页面发送ajax请求，请求服务端，根据id查询当前菜品信息，用于菜品信息回显。
     * 根据id查询菜品信息和对应的口味信息，这里前端需要返回的是菜品信息和口味信息，所以需要一个DishDto封装前端所需的返回数据。这里的R对象的泛型必须为DishDto.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){ //这个@PathVariable 注解的使用于 @GetMapping. 而同样传的是id参数的话 @PostMapping 和 @PutMapping 则要使用 @RequstParmeter 注解。
        //返回一个封装的DishDto泛型，需要查询两张表，要在服务层自定义一个查询方法。
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品二：处理前端的第4步请求. 点击保存按钮，页面发送ajax请求，将修改后的菜品相关数据以json形式提交到服务器。
     * 方法设计思路：
     *      1. 返回值，更新，修改以及保存都是类似的操作，其返回值泛型是为字符串，因为需要给前端用户显示一个成功的信息。
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        log.info("所传过来的dish对象： "+dish.toString());
        //构造查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //构造查询条件
        dishLambdaQueryWrapper
                .eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .eq(Dish::getStatus, 1)
                .orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
        //dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        //构造排序条件
        //dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
        //执行查询
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);

        List<DishDto> dishDtoList = list.stream().map(
                (item) -> {
                    //填充 dishDto 对象的属性
                    DishDto dishDto = new DishDto();
                    //把dish的一般属性拷贝到dishDto
                    BeanUtils.copyProperties(item, dishDto);
                    //把通过dish对象中获取的categoryName赋值给dishDto
                    //根据id查询分类对象
                    Long categoryId = item.getCategoryId(); //分类id
                    Category category = categoryService.getById(categoryId);
                    if (category != null) {
                        String categoryName = category.getName();
                        dishDto.setCategoryName(categoryName);
                    }
                    Long dishId = item.getId();//当前菜品id

                    LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);

                    //SQL:select * from dish_flavor where dish_id = ?
                    List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
                    dishDto.setFlavors(dishFlavorList);
                    return dishDto;
                }
        ).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

   /* @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        log.info("所传过来的dish对象： "+dish.toString());
        //构造查询条件
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //构造查询条件
        dishLambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        //构造排序条件
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
        //执行查询
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);

        return R.success(list);
    }*/

    /**
     * 菜品状态批量起售停售
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
        boolean updated = dishService.updateStatusBatch(ids, status);
        return updated ? R.success("更新成功") : R.error("更新失败");
    }

    /**
     * 根据id批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.removeByIds(ids);
        return R.success("成功删除菜品");
    }
}










