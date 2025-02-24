package com.itheima.reggie.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Dish Flavor Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "dish_flavor") // Maps to the "dish_flavor" table in the database
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "dish_id", nullable = false)
    private Long dishId; // Associated dish ID

    @Column(name = "name", nullable = false, length = 100)
    private String name; // Flavor name

    @Column(name = "value", nullable = false, length = 255)
    private String value; // Flavor details (JSON format or comma-separated list)

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted; // 0 = Not Deleted, 1 = Deleted

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
