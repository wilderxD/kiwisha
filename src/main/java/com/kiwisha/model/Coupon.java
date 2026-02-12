package com.kiwisha.model;

import jakarta.persistence.*;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Double discountPercentage;
    private Boolean active;

    public Coupon() {}

    public Coupon(Long id, String code, Double discountPercentage, Boolean active) {
        this.id = id;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }
    public Boolean getActive() { return active; }
    public boolean isActive() { return active != null && active; }
    public void setActive(Boolean active) { this.active = active; }
}
