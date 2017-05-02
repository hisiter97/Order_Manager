/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author TRONGNGHIA
 */
@Entity
@Table(name = "business_types")
public class BusinessTypeEntity implements Serializable {

    @Id
    @Column(name = "business_type_id")
    @GeneratedValue
    private Long businessTypeId;

    @Column(name = "business_type_code")
    private String businessTypeCode;

    @Column(name = "business_type_description")
    private String businessTypeDescription;

    /**
     * @return the businessTypeId
     */
    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    /**
     * @param businessTypeId the businessTypeId to set
     */
    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    /**
     * @return the businessTypeCode
     */
    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    /**
     * @param businessTypeCode the businessTypeCode to set
     */
    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    /**
     * @return the businessTypeDescription
     */
    public String getBusinessTypeDescription() {
        return businessTypeDescription;
    }

    /**
     * @param businessTypeDescription the businessTypeDescription to set
     */
    public void setBusinessTypeDescription(String businessTypeDescription) {
        this.businessTypeDescription = businessTypeDescription;
    }

    @Override
    public String toString() {
        return this.getBusinessTypeDescription(); //To change body of generated methods, choose Tools | Templates.
    }
}
