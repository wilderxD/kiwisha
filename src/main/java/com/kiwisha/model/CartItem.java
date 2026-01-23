package com.kiwisha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CartItem implements Serializable {
    private Product product;
    private Integer quantity;

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
