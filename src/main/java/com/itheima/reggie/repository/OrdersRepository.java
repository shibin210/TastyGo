package com.itheima.reggie.repository;

import com.itheima.reggie.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * Orders Repository (Hibernate Version)
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAll(@NotNull Pageable pageable);

}
