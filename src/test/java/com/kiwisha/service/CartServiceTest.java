package com.kiwisha.service;

import com.kiwisha.model.Product;
import com.kiwisha.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    @Test
    void debeAgregarProductoNuevoAlCarrito() {
        // Ajustado a los 7 parámetros de tu constructor
        Product producto = new Product(1L, "Laptop", "Gamer", 1500.0, 10, "Tech", "url");

        cartService.addProduct(producto);

        assertThat(cartService.getItems()).hasSize(1);
        assertThat(cartService.getItems().get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    void debeIncrementarCantidadSiElProductoYaExiste() {
        Product producto = new Product(1L, "Laptop", "Gamer", 1500.0, 10, "Tech", "url");
        
        cartService.addProduct(producto);
        cartService.addProduct(producto); // Segunda vez

        assertThat(cartService.getItems()).hasSize(1);
        assertThat(cartService.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    void debeEliminarProductoDelCarrito() {
        Long idEliminar = 1L;
        Product producto = new Product(idEliminar, "Laptop", "Gamer", 1500.0, 10, "Tech", "url");
        cartService.addProduct(producto);

        cartService.removeProduct(idEliminar);

        assertThat(cartService.getItems()).isEmpty();
    }
}