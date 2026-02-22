package com.kiwisha.service;

import com.kiwisha.model.CartItem;
import com.kiwisha.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
        // Inicializamos productos de kiwicha para las pruebas
        product1 = new Product();
        product1.setId(1L);
        product1.setPrice(10.0);
        product1.setName("Kiwicha Pop");

        product2 = new Product();
        product2.setId(2L);
        product2.setPrice(20.0);
        product2.setName("Barra de Kiwicha");
    }

    @Test
    @DisplayName("Debe añadir un producto nuevo al carrito")
    void testAddProductNew() {
        cartService.addProduct(product1);
        
        List<CartItem> items = cartService.getItems();
        assertEquals(1, items.size());
        assertEquals(1, items.get(0).getQuantity());
        assertEquals(product1.getId(), items.get(0).getProduct().getId());
    }

    @Test
    @DisplayName("Debe incrementar la cantidad si el producto ya existe")
    void testAddProductExisting() {
        cartService.addProduct(product1); // Primera vez
        cartService.addProduct(product1); // Segunda vez (debe entrar al 'if' y hacer 'return')

        List<CartItem> items = cartService.getItems();
        assertEquals(1, items.size(), "No debe añadir un nuevo item, solo incrementar cantidad");
        assertEquals(2, items.get(0).getQuantity());
    }

    @Test
    @DisplayName("Debe calcular el total correctamente")
    void testGetTotal() {
        // Asumiendo que CartItem.getSubtotal() multiplica precio * cantidad
        cartService.addProduct(product1); // 10.0
        cartService.addProduct(product2); // 20.0
        
        // El total debería ser 30.0
        assertEquals(30.0, cartService.getTotal());
    }

    @Test
    @DisplayName("Debe limpiar el carrito por completo")
    void testClear() {
        cartService.addProduct(product1);
        assertFalse(cartService.getItems().isEmpty());
        
        cartService.clear();
        
        assertTrue(cartService.getItems().isEmpty());
        assertEquals(0.0, cartService.getTotal());
    }

    @Test
    @DisplayName("Debe eliminar un producto específico por su ID")
    void testRemoveProduct() {
        cartService.addProduct(product1);
        cartService.addProduct(product2);
        
        cartService.removeProduct(1L); // Eliminamos producto1
        
        List<CartItem> items = cartService.getItems();
        assertEquals(1, items.size());
        assertEquals(product2.getId(), items.get(0).getProduct().getId());
    }
}