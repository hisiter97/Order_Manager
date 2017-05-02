package com.td.trongnghia.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "resources")
public class ResourceEntity implements Serializable{    

    @Id
    @Column(name = "resource_id")
    @GeneratedValue
    private Long resourceId;
    @Column(name = "resource_name")
    private String resourceName;
    @Column(name = "original_price")
    private Integer originalPrice;
    @Column(name = "rent_price")
    private Integer rentPrice;
    @Column(name = "description")
    private String description;
    @Column(name = "resource_type")
    private Integer resourceType;
    
//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "resourceEntities")
//    private List<OrderEntity> associatedOrders;

    /**
     * @return the resourceId
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId the resourceId to set
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the originalPrice
     */
    public Integer getOriginalPrice() {
        return originalPrice;
    }

    /**
     * @param originalPrice the originalPrice to set
     */
    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * @return the rentPrice
     */
    public Integer getRentPrice() {
        return rentPrice;
    }

    /**
     * @param rentPrice the rentPrice to set
     */
    public void setRentPrice(Integer rentPrice) {
        this.rentPrice = rentPrice;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the resourceType
     */
    public Integer getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType the resourceType to set
     */
    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return this.getResourceName();
    }
    
//    /**
//     * @return the associatedOrders
//     */
//    public List<OrderEntity> getAssociatedOrders() {
//        return associatedOrders;
//    }
//
//    /**
//     * @param associatedOrders the associatedOrders to set
//     */
//    public void setAssociatedOrders(List<OrderEntity> associatedOrders) {
//        this.associatedOrders = associatedOrders;
//    }
}
