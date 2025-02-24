package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.repository.DishFlavorRepository;
import com.itheima.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Dish Flavor Service Implementation (Hibernate Version)
 */
@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorRepository dishFlavorRepository;

    /**
     * Save a new dish flavor
     * @param dishFlavor DishFlavor entity
     * @return Saved DishFlavor entity
     */
    @Override
    public DishFlavor saveDishFlavor(DishFlavor dishFlavor) {
        return dishFlavorRepository.save(dishFlavor);
    }

    /**
     * Get a list of flavors associated with a dish
     * @param dishId Dish ID
     * @return List of DishFlavor entities
     */
    @Override
    public List<DishFlavor> getFlavorsByDishId(Long dishId) {
        return dishFlavorRepository.findByDishId(dishId);
    }

    /**
     * Delete all flavors associated with a dish
     * @param dishId Dish ID
     */
    @Override
    public void deleteFlavorsByDishId(Long dishId) {
        List<DishFlavor> flavors = dishFlavorRepository.findByDishId(dishId);
        dishFlavorRepository.deleteAll(flavors);
    }
}
