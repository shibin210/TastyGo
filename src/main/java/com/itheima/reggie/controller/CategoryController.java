package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Category Management Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Add a new category
     * @param category Category entity
     * @return Response with success message
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("Saving category: {}", category);
        categoryService.saveCategory(category);
        return R.success("Category added successfully");
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page     The page number (starting from 1 on the frontend, but adjusted to start from 0 internally).
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of categories.
     */
    @GetMapping("/page")
    public R<Map<String, Object>> getCategoryPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // Call the service layer to retrieve paginated data
        Map<String, Object> pageInfo = categoryService.getCategoryPage(page, pageSize);
        return R.success(pageInfo);
    }

    /**
     * Get all categories
     * @return List of categories
     */
    @GetMapping
    public R<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return R.success(categories);
    }

    /**
     * Get a category by ID
     * @param id Category ID
     * @return Response with category data
     */
    @GetMapping("/{id}")
    public R<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(R::success).orElseGet(() -> R.error("Category not found"));
    }

    /**
     * Delete a category by ID
     * @param id Category ID
     * @return Response message
     */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        categoryService.deleteCategory(id);
        return R.success("Category deleted successfully");
    }

    /**
     * Get categories by type
     * @param type Category type
     * @return List of categories
     */
    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam("type") Integer type) {
        List<Category> categories = categoryService.getCategoriesByType(type);
        return R.success(categories);
    }
}
