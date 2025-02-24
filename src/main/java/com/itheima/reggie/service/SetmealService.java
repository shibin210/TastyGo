package com.itheima.reggie.service;

import com.itheima.reggie.entity.Setmeal;
import java.util.List;
import java.util.Optional;

/**
 * Setmeal Service Interface (Hibernate Version)
 */
public interface SetmealService {

    // Save a new setmeal
    void saveSetmeal(Setmeal setmeal);

    // Get setmeal by ID
    Optional<Setmeal> getSetmealById(Long id);

    // Update setmeal
    void updateSetmeal(Setmeal setmeal);

    // Update setmeal status in batch
    boolean updateStatusBatch(List<Long> ids, Integer status);

    // List all setmeals (optionally filter by category)
    List<Setmeal> listSetmealsByCategory(Long categoryId);

    // Delete setmeals by IDs
    void deleteSetmeals(List<Long> ids);

    // Count setmeals by category ID
    int countSetmealsByCategoryId(Long categoryId);
}
