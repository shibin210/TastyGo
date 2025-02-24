package com.itheima.reggie.repository;

import com.itheima.reggie.entity.DishFlavor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Dish Flavor Repository (Hibernate Version)
 */
@Repository
public interface DishFlavorRepository extends JpaRepository<DishFlavor, Long> {

    // Find all flavors for a given dish
    List<DishFlavor> findByDishId(Long dishId);
}
