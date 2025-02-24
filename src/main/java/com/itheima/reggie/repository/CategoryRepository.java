package com.itheima.reggie.repository;

import com.itheima.reggie.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Category Repository (Hibernate Version)
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find categories by type
    List<Category> findByType(Integer type);
    Page<Category> findAll(@NotNull Pageable pageable);

}
