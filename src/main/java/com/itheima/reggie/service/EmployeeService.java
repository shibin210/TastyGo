package com.itheima.reggie.service;

import com.itheima.reggie.entity.Employee;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Employee Service Interface (Hibernate Version)
 */
public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> getEmployeeByUsername(String username);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(Long id);

    Map<String, Object> getEmployeePage(int page, int size);

}
