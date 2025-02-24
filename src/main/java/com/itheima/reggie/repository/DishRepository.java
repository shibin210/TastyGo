package com.itheima.reggie.repository;

import com.itheima.reggie.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * Dish Repository (Hibernate Version)
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    int countByCategoryId(Long categoryId);

    Page<Dish> findAll(@NotNull Pageable pageable);

}
