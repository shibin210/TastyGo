package com.itheima.reggie.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Dish Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "dish") // Maps this entity to the "dish" table in the database
public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name; // Dish name

    @Column(name = "category_id", nullable = false)
    private Long categoryId; // Category ID

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // Dish price

    @Column(name = "code", length = 50)
    private String code; // Product code

    @Column(name = "image", length = 255)
    private String image; // Dish image URL

    @Column(name = "description", length = 255)
    private String description; // Dish description

    @Column(name = "status", nullable = false)
    private Integer status; // 0 = Not Available, 1 = Available

    @Column(name = "sort", nullable = false)
    private Integer sort; // Sorting order

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted; // 0 = Not Deleted, 1 = Deleted

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime; // Creation time

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime; // Last update time

    @CreatedDate
    @Column(name = "create_user", updatable = false)
    private Long createUser; // Created by (User ID)

    @LastModifiedDate
    @Column(name = "update_user")
    private Long updateUser; // Last updated by (User ID)

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
