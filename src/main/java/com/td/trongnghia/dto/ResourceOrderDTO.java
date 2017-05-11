/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.dto;

import com.td.trongnghia.entity.ResourceEntity;

/**
 *
 * @author TRONGNGHIA
 */
public class ResourceOrderDTO {

    private ResourceEntity resourceEntity;
    private Integer quantity;
    private Double payment;

    /**
     * @return the resourceEntity
     */
    public ResourceEntity getResourceEntity() {
        return resourceEntity;
    }

    /**
     * @param resourceEntity the resourceEntity to set
     */
    public void setResourceEntity(ResourceEntity resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the payment
     */
    public Double getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Double payment) {
        this.payment = payment;
    }
}
