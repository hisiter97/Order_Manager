package com.td.trongnghia.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_ident")
    private String customerIdent;
    
    @Column(name = "customer_addr")
    private String customerAddr;

    /**
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * @param customerPhone the customerPhone to set
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * @return the customerIdent
     */
    public String getCustomerIdent() {
        return customerIdent;
    }

    /**
     * @param customerIdent the customerIdent to set
     */
    public void setCustomerIdent(String customerIdent) {
        this.customerIdent = customerIdent;
    }

    /**
     * @return the customerAddr
     */
    public String getCustomerAddr() {
        return customerAddr;
    }

    /**
     * @param customerAddr the customerAddr to set
     */
    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    @Override
    public String toString() {
        return this.customerName + "-" + this.customerPhone + "-" + this.customerIdent; //To change body of generated methods, choose Tools | Templates.
    }

}
