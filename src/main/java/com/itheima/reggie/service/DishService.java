package com.itheima.reggie.service;

import com.itheima.reggie.entity.Dish;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dish Service Interface (Hibernate Version)
 */
public interface DishService {

    // Save a new dish
    void saveDish(Dish dish);

    // Get dish by ID
    Optional<Dish> getDishById(Long id);

    // Update dish
    void updateDish(Dish dish);

    // Update dish status in batch
    boolean updateDishStatusBatch(List<Long> ids, Integer status);

    // List dishes with filtering options
    List<Dish> listDishes(Dish dish);

    // Delete dishes by IDs
    void deleteDishes(List<Long> ids);

    // Count dishes by category
    int countDishesByCategoryId(Long categoryId);

    Map<String, Object> getDishPage(int page, int size);

}
