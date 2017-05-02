/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.td.trongnghia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author TRONGNGHIA
 */
@Entity
@Table(name = "orders_resources")
public class OrderResourceEntity {

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

    @Id
    @Column(name = "orders_resources_id")
    @GeneratedValue
    private Long ordersResourcesId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @OneToOne
    @JoinColumn(name = "resource_id")
    private ResourceEntity resourceEntity;
    
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * @return the ordersResourcesId
     */
    public Long getOrdersResourcesId() {
        return ordersResourcesId;
    }

    /**
     * @param ordersResourcesId the ordersResourcesId to set
     */
    public void setOrdersResourcesId(Long ordersResourcesId) {
        this.ordersResourcesId = ordersResourcesId;
    }

    /**
     * @return the orderEntity
     */
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    /**
     * @param orderEntity the orderEntity to set
     */
    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

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
}
