package com.td.trongnghia.entity;

import javax.persistence.*;

/**
 * Created by TRONGNGHIA on 3/28/2017.
 */
@Entity
@Table(name = "resources")
public class ResourceEntity {
    @Id
    @Column(name = "resource_id")
    @GeneratedValue
    private Long resourceId;
    @Column(name = "resource_name")
    private String resourceName;
    @Column(name = "original_price")
    private Integer originalPrice;
    @Column(name = "price_per_hour")
    private Integer pricePerHour;
    @Column(name = "price_per_day")
    private Integer pricePerDay;
    @Column(name = "price_description")
    private String description;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
