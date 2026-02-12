package com.kiwisha.model;

import java.io.Serializable;

/**
 * CartItem class.
 */
public class CartItem implements Serializable {
  private Product product;
  private Integer quantity;

  public CartItem() {
  }

  public CartItem(Product product, Integer quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getSubtotal() {
    return product.getPrice() * quantity;
  }
}
