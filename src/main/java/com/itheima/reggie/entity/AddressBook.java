package com.itheima.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Address Book Entity
 */
@Data
@Entity
@Table(name = "address_book") // Specifies the database table name
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // User ID

    @Column(name = "consignee", nullable = false, length = 50)
    private String consignee; // Recipient name

    @Column(name = "phone", nullable = false, length = 20)
    private String phone; // Phone number

    @Column(name = "sex", length = 1)
    private String sex; // Gender (0 = Female, 1 = Male)

    @Column(name = "province_code", length = 20)
    private String provinceCode; // Province code

    @Column(name = "province_name", length = 50)
    private String provinceName; // Province name

    @Column(name = "district_code", length = 20)
    private String districtCode; // District code

    @Column(name = "district_name", length = 50)
    private String districtName; // District name

    @Column(name = "detail")
    private String detail; // Detailed address

    @Column(name = "label", length = 20)
    private String label; // Address label (e.g., Home, Office)

    @Column(name = "is_default")
    private Integer isDefault; // Default address flag (0 = No, 1 = Yes)

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

    @Column(name = "is_deleted")
    private Integer isDeleted; // Deletion status (0 = Not deleted, 1 = Deleted)

    /**
     * Automatically set creation and update timestamps before persisting
     */
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    /**
     * Automatically update the timestamp before modifying
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}


/*

*/
/**
 * 地址簿实体类
 *//*

@Data
public class AddressBook implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;

    //用户id
    private Long userId;

    //收货人
    private String consignee;

    //手机号
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //省级区划编号
    private String provinceCode;

    //省级名称
    private String provinceName;

    //区级区划编号
    private String districtCode;

    //区级名称
    private String districtName;

    //详细地址
    private String detail;

    //标签
    private String label;

    //是否默认 0 否 1 是
    private Integer isDefault;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //删除状态
    private Integer isDeleted;
}

*/













