package com.itheima.reggie.repository;

import com.itheima.reggie.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Employee Repository (Hibernate Version)
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Find employee by username
    Optional<Employee> findByUsername(String username);

    Page<Employee> findAll(@NotNull Pageable pageable);

}
