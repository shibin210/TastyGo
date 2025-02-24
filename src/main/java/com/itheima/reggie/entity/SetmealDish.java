package com.itheima.reggie.entity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Setmeal-Dish Relationship Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "setmeal_dish")
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Relationship ID

    @Column(name = "setmeal_id", nullable = false)
    private Long setmealId; // Associated Setmeal ID

    @Column(name = "name")
    private String name; // Dish Name (Redundant Field)

    @Column(name = "price", nullable = false)
    private BigDecimal price; // Dish Price

    @Column(name = "dish_id", nullable = false)
    private Long dishId; // Dish ID

    @Column(name = "copies", nullable = false)
    private Integer copies; // Quantity

    @Column(name = "sort", nullable = false)
    private Integer sort; // Sorting Order

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
