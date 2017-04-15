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
    @JoinColumn(name = "resource_order")
    private ResourceEntity resourceEntity;
    @OneToOne
    @JoinColumn(name = "shipper")
    private UserEntity shipper;
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Column(name = "date_shipped")
    private Timestamp dateShipped;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @OneToOne
    @JoinColumn(name = "user_created")
    private UserEntity userCreated;

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

    public Timestamp getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Timestamp dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public UserEntity getShipper() {
        return shipper;
    }

    public void setShipper(UserEntity shipper) {
        this.shipper = shipper;
    }
    
    /**
     * @return the userCreated
     */
    public UserEntity getUserCreated() {
        return userCreated;
    }

    /**
     * @param userCreated the userCreated to set
     */
    public void setUserCreated(UserEntity userCreated) {
        this.userCreated = userCreated;
    }
}
