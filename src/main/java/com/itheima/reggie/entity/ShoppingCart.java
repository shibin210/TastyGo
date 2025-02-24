package com.itheima.reggie.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Shopping Cart Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Shopping Cart ID

    @Column(name = "name", nullable = false)
    private String name; // Product Name

    @Column(name = "user_id", nullable = false)
    private Long userId; // User ID

    @Column(name = "dish_id")
    private Long dishId; // Dish ID (Nullable)

    @Column(name = "setmeal_id")
    private Long setmealId; // Setmeal ID (Nullable)

    @Column(name = "dish_flavor")
    private String dishFlavor; // Dish Flavor

    @Column(name = "number", nullable = false)
    private Integer number; // Quantity

    @Column(name = "amount", nullable = false)
    private BigDecimal amount; // Total Amount

    @Column(name = "image")
    private String image; // Image URL

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime; // Created Timestamp
}
