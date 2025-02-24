package com.itheima.reggie.entity;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Orders Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Order ID

    @Column(name = "number", unique = true, nullable = false)
    private String number; // Order Number

    @Column(name = "status", nullable = false)
    private Integer status; // Order Status (1: Pending, 2: Paid, 3: Dispatched, 4: Completed, 5: Cancelled)

    @Column(name = "user_id", nullable = false)
    private Long userId; // User ID

    @Column(name = "address_book_id", nullable = false)
    private Long addressBookId; // Address ID

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime; // Order Time

    @Column(name = "checkout_time")
    private LocalDateTime checkoutTime; // Checkout Time

    @Column(name = "pay_method", nullable = false)
    private Integer payMethod; // Payment Method (1: WeChat, 2: Alipay)

    @Column(name = "amount", nullable = false)
    private BigDecimal amount; // Total Order Amount

    @Column(name = "remark")
    private String remark; // Order Notes

    @Column(name = "phone")
    private String phone; // User Phone

    @Column(name = "address")
    private String address; // Delivery Address

    @Column(name = "user_name")
    private String userName; // User Name

    @Column(name = "consignee")
    private String consignee; // Consignee Name
}
