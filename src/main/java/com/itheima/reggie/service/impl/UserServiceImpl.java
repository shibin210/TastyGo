package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.User;
import com.itheima.reggie.repository.UserRepository;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * User Service Implementation (Hibernate Version)
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
