package com.itheima.reggie.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Category Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "type", nullable = false)
    private Integer type; // Type: 1 = Dish, 2 = Set Meal

    @Column(name = "name", nullable = false, length = 50)
    private String name; // Category Name

    @Column(name = "sort", nullable = false)
    private Integer sort; // Sort Order

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime; // Creation Time

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime; // Last Update Time

    @CreatedDate
    @Column(name = "create_user", updatable = false)
    private Long createUser; // Created By

    @LastModifiedDate
    @Column(name = "update_user")
    private Long updateUser; // Updated By

    /**
     * Auto-set timestamps before saving
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
