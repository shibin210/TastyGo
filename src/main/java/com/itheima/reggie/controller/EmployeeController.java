package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Employee Management Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Employee login
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        String username = employee.getUsername();
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        Optional<Employee> optionalEmp = employeeService.getEmployeeByUsername(username);

        if (optionalEmp.isEmpty()) {
            return R.error("Username not found");
        }

        Employee emp = optionalEmp.get();
        if (!emp.getPassword().equals(password)) {
            return R.error("Incorrect password");
        }

        if (emp.getStatus() == 0) {
            return R.error("Employee account is disabled");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * Employee logout
     */
    @PostMapping("/logout")
    public R<String> logOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.success("Logout successful");
    }

    /**
     * Add a new employee
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        log.info("New employee: {}", employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeService.saveEmployee(employee);
        return R.success("Employee added successfully");
    }

    /**
     * Get employee by ID
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(R::success).orElseGet(() -> R.error("Employee not found"));
    }

    /**
     * Update employee details
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        log.info("Updating employee: {}", employee);
        employeeService.updateEmployee(employee);
        return R.success("Employee updated successfully");
    }

    /**
     * Get all employees
     */
    @GetMapping("/list")
    public R<List<Employee>> getAll() {
        List<Employee> employees = employeeService.getAllEmployees();
        return R.success(employees);
    }

    /**
     * Delete an employee by ID
     */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return R.success("Employee deleted successfully");
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
        Map<String, Object> pageInfo = employeeService.getEmployeePage(page, pageSize);
        return R.success(pageInfo);
    }
}
