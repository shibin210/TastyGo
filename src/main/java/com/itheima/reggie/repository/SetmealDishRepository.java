package com.itheima.reggie.repository;

import com.itheima.reggie.entity.SetmealDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Setmeal Dish Repository (Hibernate Version)
 */
@Repository
public interface SetmealDishRepository extends JpaRepository<SetmealDish, Long> {
    List<SetmealDish> findBySetmealId(Long setmealId);
}
