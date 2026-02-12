package com.kiwisha.model;

import jakarta.persistence.*;

@Entity
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userEmail;
    
    @ManyToOne
    private Product product;

    public WishlistItem() {}

    public WishlistItem(String userEmail, Product product) {
        this.userEmail = userEmail;
        this.product = product;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
