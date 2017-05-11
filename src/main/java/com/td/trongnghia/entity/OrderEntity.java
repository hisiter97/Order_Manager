package com.td.trongnghia.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    @OneToOne
    @JoinColumn(name = "receiver")
    private UserEntity receiver;
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Column(name = "date_modified")
    private Timestamp dateModified;
    @Column(name = "date_shipped")
    private Timestamp dateShipped;
    @Column(name = "date_returned")
    private Timestamp dateReturned;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;
    @OneToOne
    @JoinColumn(name = "business_type_id")
    private BusinessTypeEntity businessTypeEntity;
    @OneToOne
    @JoinColumn(name = "user_created")
    private UserEntity userCreated;
    @Column(name = "planned_payment")
    private Double plannedPayment = 0.0;
    @Column(name = "supp_payment")
    private Double suppPayment = 0.0;
    @Column(name = "final_payment")
    private Double finalPayment = 0.0;
    @Column(name = "shipper_payment")
    private Double shipperPayment = 0.0;
    @Column(name = "receiver_payment")
    private Double receiverPayment = 0.0;
    @Column(name = "status")
    private Integer status = 0;
    @Column(name = "ship_payment")
    private Double shipPayment = 0.0;
    @Column(name = "deposit")
    private Integer deposit;

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
     * @return the receiver
     */
    public UserEntity getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
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
     * @return the dateModified
     */
    public Timestamp getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
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
     * @return the dateReturned
     */
    public Timestamp getDateReturned() {
        return dateReturned;
    }

    /**
     * @param dateReturned the dateReturned to set
     */
    public void setDateReturned(Timestamp dateReturned) {
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
     * @return the plannedPayment
     */
    public Double getPlannedPayment() {
        return plannedPayment;
    }

    /**
     * @param plannedPayment the plannedPayment to set
     */
    public void setPlannedPayment(Double plannedPayment) {
        this.plannedPayment = plannedPayment;
    }

    /**
     * @return the suppPayment
     */
    public Double getSuppPayment() {
        return suppPayment;
    }

    /**
     * @param suppPayment the suppPayment to set
     */
    public void setSuppPayment(Double suppPayment) {
        this.suppPayment = suppPayment;
    }

    /**
     * @return the finalPayment
     */
    public Double getFinalPayment() {
        return finalPayment;
    }

    /**
     * @param finalPayment the finalPayment to set
     */
    public void setFinalPayment(Double finalPayment) {
        this.finalPayment = finalPayment;
    }

    /**
     * @return the shipperPayment
     */
    public Double getShipperPayment() {
        return shipperPayment;
    }

    /**
     * @param shipperPayment the shipperPayment to set
     */
    public void setShipperPayment(Double shipperPayment) {
        this.shipperPayment = shipperPayment;
    }

    /**
     * @return the receiverPayment
     */
    public Double getReceiverPayment() {
        return receiverPayment;
    }

    /**
     * @param receiverPayment the receiverPayment to set
     */
    public void setReceiverPayment(Double receiverPayment) {
        this.receiverPayment = receiverPayment;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public StringProperty finalPayment() {
        return new SimpleStringProperty(finalPayment.toString());
    }

    /**
     * @return the shipPayment
     */
    public Double getShipPayment() {
        return shipPayment;
    }

    /**
     * @param shipPayment the shipPayment to set
     */
    public void setShipPayment(Double shipPayment) {
        this.shipPayment = shipPayment;
    }

    /**
     * @return the deposit
     */
    public Integer getDeposit() {
        return deposit;
    }

    /**
     * @param deposit the deposit to set
     */
    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }
}
