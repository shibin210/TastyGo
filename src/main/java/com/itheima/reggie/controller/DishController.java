package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dish Management Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Save a new dish
     * @param dish Dish entity
     * @return Response message
     */
    @PostMapping
    public R<String> save(@RequestBody Dish dish) {
        log.info("Saving dish: {}", dish);
        dishService.saveDish(dish);
        return R.success("Dish added successfully");
    }

    /**
     * Get dish by ID
     * @param id Dish ID
     * @return Dish entity
     */
    @GetMapping("/{id}")
    public R<Dish> get(@PathVariable Long id) {
        Optional<Dish> dish = dishService.getDishById(id);
        return dish.map(R::success).orElseGet(() -> R.error("Dish not found"));
    }

    /**
     * Update an existing dish
     * @param dish Dish entity
     * @return Response message
     */
    @PutMapping
    public R<String> update(@RequestBody Dish dish) {
        log.info("Updating dish: {}", dish);
        dishService.updateDish(dish);
        return R.success("Dish updated successfully");
    }

    /**
     * Delete dishes by IDs
     * @param ids List of dish IDs
     * @return Response message
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.deleteDishes(ids);
        return R.success("Dishes deleted successfully");
    }

    /**
     * Get list of dishes (optional filtering by category)
     * @param categoryId Category ID
     * @return List of dishes
     */
    @GetMapping("/list")
    public R<List<Dish>> list(@RequestParam(required = false) Long categoryId) {
        Dish filter = new Dish();
        if (categoryId != null) {
            filter.setCategoryId(categoryId);
        }
        List<Dish> dishes = dishService.listDishes(filter);
        return R.success(dishes);
    }

    /**
     * Batch update dish status (e.g., enable/disable)
     * @param ids List of dish IDs
     * @param status New status (0 = Disabled, 1 = Enabled)
     * @return Response message
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {
        boolean updated = dishService.updateDishStatusBatch(ids, status);
        return updated ? R.success("Dish status updated successfully") : R.error("Failed to update dish status");
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page     The page number (starting from 1 on the frontend, but adjusted to start from 0 internally).
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of categories.
     */
    @GetMapping("/page")
    public R<Map<String, Object>> getDishPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // Call the service layer to retrieve paginated data
        Map<String, Object> pageInfo = dishService.getDishPage(page, pageSize);
        return R.success(pageInfo);
    }
}
