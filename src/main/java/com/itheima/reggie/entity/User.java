package com.itheima.reggie.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User Entity (Hibernate Version)
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User ID

    @Column(name = "name", length = 50)
    private String name; // User Name

    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone; // Phone Number

    @Column(name = "sex", length = 1)
    private String sex; // Gender (0 = Female, 1 = Male)

    @Column(name = "id_number", unique = true, length = 18)
    private String idNumber; // Identity Card Number

    @Column(name = "avatar")
    private String avatar; // Avatar URL

    @Column(name = "status", nullable = false)
    private Integer status; // 0 = Disabled, 1 = Active
}
