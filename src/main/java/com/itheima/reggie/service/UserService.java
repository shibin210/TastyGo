package com.itheima.reggie.service;

import com.itheima.reggie.entity.User;
import java.util.Optional;

/**
 * User Service Interface (Hibernate Version)
 */
public interface UserService {
    Optional<User> getUserByPhone(String phone);
    User saveUser(User user);
    User registerOrRetrieveUser(String phone);

}
