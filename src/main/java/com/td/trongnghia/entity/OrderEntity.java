package com.td.trongnghia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long orderId;
    @OneToOne
    @JoinColumn(name = "shipper")
    private UserEntity shipper;
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Column(name = "date_shipped")
    private Timestamp dateShipped;
    @Column(name = "date_returned")
    private Date dateReturned;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;
    @OneToOne
    @JoinColumn(name = "business_type_id")
    private BusinessTypeEntity businessTypeEntity;
    @OneToOne
    @JoinColumn(name = "user_created")
    private UserEntity userCreated;
    @Column(name = "payment")
    private Integer payment;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "orders_resources", joinColumns = {
//        @JoinColumn(name = "order_id")},
//            inverseJoinColumns = {
//                @JoinColumn(name = "resource_id")})
//    private List<ResourceEntity> resourceEntities;

    /**
     * @return the orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the shipper
     */
    public UserEntity getShipper() {
        return shipper;
    }

    /**
     * @param shipper the shipper to set
     */
    public void setShipper(UserEntity shipper) {
        this.shipper = shipper;
    }

    /**
     * @return the dateCreated
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the dateShipped
     */
    public Timestamp getDateShipped() {
        return dateShipped;
    }

    /**
     * @param dateShipped the dateShipped to set
     */
    public void setDateShipped(Timestamp dateShipped) {
        this.dateShipped = dateShipped;
    }

    /**
     * @return the dateReturn
     */
    public Date getDateReturned() {
        return dateReturned;
    }

    /**
     * @param dateReturn the dateReturn to set
     */
    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    /**
     * @return the customerEntity
     */
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    /**
     * @param customerEntity the customerEntity to set
     */
    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    /**
     * @return the businessTypeEntity
     */
    public BusinessTypeEntity getBusinessTypeEntity() {
        return businessTypeEntity;
    }

    /**
     * @param businessTypeEntity the businessTypeEntity to set
     */
    public void setBusinessTypeEntity(BusinessTypeEntity businessTypeEntity) {
        this.businessTypeEntity = businessTypeEntity;
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

    /**
     * @return the payment
     */
    public Integer getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Integer payment) {
        this.payment = payment;
    }

//    /**
//     * @return the resourceEntities
//     */
//    public List<ResourceEntity> getResourceEntities() {
//        return resourceEntities;
//    }
//
//    /**
//     * @param resourceEntities the resourceEntities to set
//     */
//    public void setResourceEntities(List<ResourceEntity> resourceEntities) {
//        this.resourceEntities = resourceEntities;
//    }
}
