package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.repository.DishRepository;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dish Service Implementation (Hibernate Version)
 */
@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public boolean updateDishStatusBatch(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null) {
            return false;
        }
        for (Long id : ids) {
            Optional<Dish> optionalDish = dishRepository.findById(id);
            optionalDish.ifPresent(dish -> {
                dish.setStatus(status);
                dishRepository.save(dish);
            });
        }
        return true;
    }

    @Override
    public List<Dish> listDishes(Dish dish) {
        return dishRepository.findAll();
    }

    @Override
    public void deleteDishes(List<Long> ids) {
        dishRepository.deleteAllById(ids);
    }

    @Override
    public int countDishesByCategoryId(Long categoryId) {
        return dishRepository.countByCategoryId(categoryId);
    }

    /**
     * Spring Data JPA Pagination Implementation (Compatible with MyBatis-Plus Format)
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getDishPage(int page, int pageSize) {
        /**
         * Step 1: Adjust Page Number for Spring Data JPA
         * Spring Data JPA uses 0-based page indexing (first page is 0).
         * Frontend usually uses 1-based indexing (first page is 1).
         * We subtract 1 from 'page' to align with Spring Data JPA's expectations.
         */
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        /**
         * Step 2: Fetch Paginated Data from Database
         * 'findAll(Pageable pageable)' is a built-in method in JpaRepository.
         * It automatically generates the correct SQL query with 'LIMIT' and 'OFFSET' for pagination.
         * The 'Page<Category>' object contains all the required pagination metadata.
         */
        Page<Dish> categoryPage = dishRepository.findAll(pageable);

        /**
         * Step 3: Construct Response in MyBatis-Plus Compatible Format
         * The frontend was originally designed to work with MyBatis-Plus.
         * MyBatis-Plus uses 'records' instead of 'content', and expects 'current' instead of 'number'.
         */
        Map<String, Object> result = new HashMap<>();

        // 🚀 Key: Use "records" instead of "content" to match the frontend's expectations.
        result.put("records", categoryPage.getContent());

        // 📝 Total number of records in the entire dataset (across all pages).
        result.put("total", categoryPage.getTotalElements());

        // 📏 Page size (number of records per page).
        result.put("size", categoryPage.getSize());

        // 🔢 Current page number (converted to 1-based index).
        result.put("current", categoryPage.getNumber() + 1);

        /**
         * Step 4: Return the formatted response
         * This ensures that the frontend can continue to work without changes.
         */
        return result;
    }
}
