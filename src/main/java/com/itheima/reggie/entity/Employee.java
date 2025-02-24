package com.itheima.reggie.entity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Employee Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "employee") // Maps to the "employee" table in the database
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name; // Employee name

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username; // Unique username

    @Column(name = "password", nullable = false, length = 255)
    private String password; // Encrypted password

    @Column(name = "phone", length = 20)
    private String phone; // Phone number

    @Column(name = "sex", length = 1)
    private String sex; // Gender (M/F)

    @Column(name = "id_number", unique = true, length = 18)
    private String idNumber; // Employee ID number

    @Column(name = "status", nullable = false)
    private Integer status; // 0 = Inactive, 1 = Active

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime; // Creation timestamp

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime; // Last update timestamp

    @CreatedDate
    @Column(name = "create_user", updatable = false)
    private Long createUser; // Created by (User ID)

    @LastModifiedDate
    @Column(name = "update_user")
    private Long updateUser; // Updated by (User ID)

    /**
     * Automatically set timestamps before saving
     */
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
