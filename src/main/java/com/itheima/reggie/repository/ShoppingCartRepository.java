package com.itheima.reggie.repository;

import com.itheima.reggie.entity.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Shopping Cart Repository (Hibernate Version)
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUserId(Long userId);

    Page<ShoppingCart> findAll(@NotNull Pageable pageable);

}
