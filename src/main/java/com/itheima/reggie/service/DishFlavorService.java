package com.itheima.reggie.service;

import com.itheima.reggie.entity.DishFlavor;
import java.util.List;

/**
 * Dish Flavor Service Interface (Hibernate Version)
 */
public interface DishFlavorService {
    DishFlavor saveDishFlavor(DishFlavor dishFlavor);
    List<DishFlavor> getFlavorsByDishId(Long dishId);
    void deleteFlavorsByDishId(Long dishId);
}
