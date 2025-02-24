package com.itheima.reggie.service.impl;

import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.repository.CategoryRepository;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Category Service Implementation (Hibernate Version)
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoriesByType(Integer type) {
        return categoryRepository.findByType(type);
    }

    @Override
    public void deleteCategory(Long id) {
        // Check if any dishes are linked to this category
        if (dishService.countDishesByCategoryId(id) > 0) {
            throw new CustomException("This category is linked to dishes and cannot be deleted.");
        }
        // Check if any set meals are linked to this category
        if (setmealService.countSetmealsByCategoryId(id) > 0) {
            throw new CustomException("This category is linked to set meals and cannot be deleted.");
        }
        // Delete the category
        categoryRepository.deleteById(id);
    }

    /**
     * Spring Data JPA Pagination Implementation (Compatible with MyBatis-Plus Format)
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getCategoryPage(int page, int pageSize) {
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
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

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
