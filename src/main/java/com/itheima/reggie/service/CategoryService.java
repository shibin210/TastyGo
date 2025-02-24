package com.itheima.reggie.service;

import com.itheima.reggie.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Category Service Interface (Hibernate Version)
 */
public interface CategoryService {
    Category saveCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> getCategoriesByType(Integer type);
    void deleteCategory(Long id);

    Map<String, Object> getCategoryPage(int page, int size);
}
