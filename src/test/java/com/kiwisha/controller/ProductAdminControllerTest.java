package com.kiwisha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ProductAdminController.class)
class ProductAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe retornar estadísticas incluyendo el conteo de stock bajo")
    void testGetStats() throws Exception {
        // Creamos productos para cubrir las dos ramas del filtro (stock < 5)
        Product p1 = new Product(); p1.setStock(3);  // Rama true
        Product p2 = new Product(); p2.setStock(10); // Rama false
        
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/admin/dashboard/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSales", is(1500.50)))
                .andExpect(jsonPath("$.ordersCount", is(24)))
                .andExpect(jsonPath("$.lowStockAlerts", is(1))); // Solo p1 cumple

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe agregar un nuevo producto")
    void testAddProduct() throws Exception {
        Product product = new Product(null, "Barra Kiwicha", "Nutritiva", 2.0, 50, "Snack", "url");
        Product savedProduct = new Product(1L, "Barra Kiwicha", "Nutritiva", 2.0, 50, "Snack", "url");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Barra Kiwicha")));
    }

    @Test
    @DisplayName("Debe actualizar un producto existente")
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        
        Product updatedDetails = new Product();
        updatedDetails.setName("Kiwicha Premium");
        updatedDetails.setPrice(5.0);
        updatedDetails.setStock(100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put("/api/admin/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Kiwicha Premium")))
                .andExpect(jsonPath("$.price", is(5.0)))
                .andExpect(jsonPath("$.stock", is(100)));
    }

    @Test
    @DisplayName("Debe eliminar un producto y retornar mensaje de confirmación")
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/admin/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado: " + productId));

        verify(productRepository, times(1)).deleteById(productId);
    }
}
