/*
package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    */
/**
     * 员工登入
     *
     * @param employee
     * @param request
     * @return
     *//*

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {

        String username = employee.getUsername();
        String password = employee.getPassword();

        //1.将页面提交的密码 password 进行 md5 加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名 username 查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有查询到则返回用户名不正确
        if (emp == null) {
            return R.error("用户名不正确");
        }

        //4.密码对比，如果不一致则返回密码错误，数据库中查的密码和传过来的处理后的加密密码进行比对
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("员工账号已禁用");
        }

        //6.登入成功，将员工id存入 Session 并返回登入成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    */
/**
     * 员工退出
     *
     * @param request
     * @return
     *//*

    @PostMapping("/logout")
    public R<String> logOut(HttpServletRequest request) {
        //销毁 Session
        request.getSession().invalidate();
        return R.success("退出成功");
    }

    */
/**
     * 新增员工
     * @param employee
     * @return
     *//*

    @PostMapping()
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}", employee.toString());

        //设置初始密码123456，需要进行 md5 加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

      */
/*  employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
*//*

        //获取当前登录用户的id
      */
/*  Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
*//*

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    */
/**
     * 员工信息的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     *//*

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    */
/**
     * 根据id修改员工信息，修改员工账号状态
     * @param employee
     * @return
     *//*

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
       // Long empId = (Long) request.getSession().getAttribute("employee");

       //employee.setUpdateTime(LocalDateTime.now());
       // employee.setUpdateUser(empId);

        // 通过 currentThread() 来获取当前线程id
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);

        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    */
/**
     * 根据id查找员工并回显员工
     * @param id
     * @return
     *//*

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){

        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("找不到这个员工");
    }
}

















*/
