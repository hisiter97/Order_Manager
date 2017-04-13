package com.td.trongnghia.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long orderId;
    @OneToOne
    @JoinColumn(name = "resource_id")
    private ResourceEntity resourceEntity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Column(name = "date_ordered")
    private Timestamp dateOrdered;
    @Column(name = "customer_name")
    private String customer_name;
    @Column(name = "customer_phone")
    private String customerPhone;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Timestamp dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ResourceEntity getResourceEntity() {
        return resourceEntity;
    }

    public void setResourceEntity(ResourceEntity resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
