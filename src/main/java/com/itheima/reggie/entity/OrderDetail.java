package com.itheima.reggie.entity;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Order Detail Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Order Detail ID

    @Column(name = "order_id", nullable = false)
    private Long orderId; // Associated Order ID

    @Column(name = "dish_id")
    private Long dishId; // Dish ID (if applicable)

    @Column(name = "setmeal_id")
    private Long setmealId; // Setmeal ID (if applicable)

    @Column(name = "name", nullable = false)
    private String name; // Dish/Setmeal Name

    @Column(name = "image")
    private String image; // Product Image

    @Column(name = "dish_flavor")
    private String dishFlavor; // Flavor Details

    @Column(name = "number", nullable = false)
    private Integer number; // Quantity Purchased

    @Column(name = "amount", nullable = false)
    private BigDecimal amount; // Subtotal Amount (Price * Quantity)
}
