package com.itheima.reggie.repository;

import com.itheima.reggie.entity.Setmeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Setmeal Repository (Hibernate Version)
 */
@Repository
public interface SetmealRepository extends JpaRepository<Setmeal, Long> {
    List<Setmeal> findByCategoryId(Long categoryId);

    int countByCategoryId(Long categoryId);
}
