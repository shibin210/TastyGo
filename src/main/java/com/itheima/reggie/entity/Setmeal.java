package com.itheima.reggie.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Setmeal Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "setmeal")
public class Setmeal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Setmeal ID

    @Column(name = "name", nullable = false)
    private String name; // Setmeal Name

    @Column(name = "category_id", nullable = false)
    private Long categoryId; // Category ID

    @Column(name = "price", nullable = false)
    private BigDecimal price; // Setmeal Price

    @Column(name = "code", nullable = false)
    private String code; // Product Code

    @Column(name = "image")
    private String image; // Image URL

    @Column(name = "description")
    private String description; // Description

    @Column(name = "status", nullable = false)
    private Integer status; // 0 = Unavailable, 1 = Available

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted; // 0 = Active, 1 = Deleted

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime; // Created Timestamp

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime; // Updated Timestamp

    @CreatedDate
    @Column(name = "create_user", updatable = false)
    private Long createUser; // Created By

    @LastModifiedDate
    @Column(name = "update_user")
    private Long updateUser; // Updated By

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
