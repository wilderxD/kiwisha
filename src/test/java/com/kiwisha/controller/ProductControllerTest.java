package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Debe listar todos los productos cuando no hay filtros")
    void testListProductsAll() throws Exception {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeDoesNotExist("query", "category"));

        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Debe filtrar por nombre cuando el parámetro 'q' está presente")
    void testListProductsByQuery() throws Exception {
        String query = "kiwicha";
        when(productRepository.findByNameContainingIgnoreCase(query)).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/productos").param("q", query))
                .andExpect(status().isOk())
                .andExpect(model().attribute("query", query));

        verify(productRepository).findByNameContainingIgnoreCase(query);
    }

    @Test
    @DisplayName("Debe filtrar por categoría cuando 'categoria' está presente y 'q' no")
    void testListProductsByCategory() throws Exception {
        String cat = "Snacks";
        when(productRepository.findByCategory(cat)).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/productos").param("categoria", cat))
                .andExpect(status().isOk())
                .andExpect(model().attribute("category", cat));

        verify(productRepository).findByCategory(cat);
    }

    @Test
    @DisplayName("Debe mostrar detalle del producto si existe")
    void testProductDetailSuccess() throws Exception {
        Product p = new Product();
        p.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-detail"))
                .andExpect(model().attribute("product", p));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el producto no existe (Cubre el orElseThrow)")
    void testProductDetailNotFound() {
        // 1. Preparamos el mock
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Verificamos que al ejecutar la petición, se lanza la excepción
        // MockMvc suele envolver las excepciones en ServletException
        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/productos/99"));
        });

        // 3. Verificamos que la causa raíz sea nuestra IllegalArgumentException
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertTrue(exception.getCause().getMessage().contains("Producto no encontrado"));
    }
}
