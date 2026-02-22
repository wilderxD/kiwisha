package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartService cartService;

   
    @Test
    @DisplayName("Debe agregar producto de kiwicha al carrito si tiene stock")
    void testAddToCartWithStock() throws Exception {
        Product barrasKiwicha = new Product(1L, "Barras Energéticas", "Kiwicha natural", 5.0, 10, "Snacks", "url");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(barrasKiwicha));

        mockMvc.perform(post("/carrito/agregar").param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/productos?added=true"));

        verify(cartService, times(1)).addProduct(any(Product.class));
    }

    @Test
    @DisplayName("No debe agregar al carrito si el stock es 0")
    void testAddToCartNoStock() throws Exception {
        Product cerealKiwicha = new Product(2L, "Cereal Kiwicha", "Sin stock", 12.0, 0, "Cereales", "url");
        
        when(productRepository.findById(2L)).thenReturn(Optional.of(cerealKiwicha));

        mockMvc.perform(post("/carrito/agregar").param("productId", "2"))
                .andExpect(status().is3xxRedirection());

        // Verificamos que NO se llamó al servicio por falta de stock
        verify(cartService, never()).addProduct(any(Product.class));
    }
  

    @Test
    @DisplayName("Debe mostrar la vista del carrito con los totales")
    void testViewCart() throws Exception {
        when(cartService.getItems()).thenReturn(new ArrayList<>());
        when(cartService.getTotal()).thenReturn(0.0);

        mockMvc.perform(get("/carrito"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cart", "total"));
    }


    @Test
    @DisplayName("Debe eliminar el producto si la cantidad actualizada es 0 o menor")
    void testUpdateQuantityToRemove() throws Exception {
        mockMvc.perform(post("/carrito/actualizar")
                .param("productId", "1")
                .param("quantity", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));

        verify(cartService, times(1)).removeProduct(1L);
    }
    
    @Test
    @DisplayName("Debe eliminar un item específico por ID")
    void testRemoveItem() throws Exception {
        mockMvc.perform(get("/carrito/eliminar/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));

        verify(cartService).removeProduct(1L);
    }
}
