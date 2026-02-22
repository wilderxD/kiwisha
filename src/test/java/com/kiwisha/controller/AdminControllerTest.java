package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.OrderRepository;
import com.kiwisha.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Debe cargar el dashboard con estadísticas correctas y productos con stock bajo")
    void testDashboard() throws Exception {
        // Creamos productos usando tu constructor para cubrir la lógica de 'lowStock'
        // p1: Stock < 5 (Debe contarse)
        Product p1 = new Product(1L, "Laptop", "Gaming", 1500.0, 3, "Tech", "url1");
        // p2: Stock >= 5 (No debe contarse)
        Product p2 = new Product(2L, "Mouse", "Wireless", 25.0, 10, "Tech", "url2");
        
        List<Product> products = Arrays.asList(p1, p2);

        when(orderRepository.count()).thenReturn(10L);
        when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-dashboard"))
                .andExpect(model().attribute("totalSales", 1500.50))
                .andExpect(model().attribute("ordersCount", 10L))
                .andExpect(model().attribute("products", products))
                .andExpect(model().attribute("lowStock", 1L)); // Solo p1 cumple la condición

        verify(orderRepository, times(1)).count();
        verify(productRepository, times(2)).findAll(); // Se llama dos veces en tu controlador
    }

    @Test
    @DisplayName("Debe guardar un producto y redireccionar al dashboard")
    void testSaveProduct() throws Exception {
        mockMvc.perform(post("/admin/product/save")
                .param("name", "Smartphone")
                .param("price", "800.0")
                .param("stock", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"));

        // Verificamos que se llamó al método save del repositorio
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Debe eliminar un producto por ID y redireccionar")
    void testDeleteProduct() throws Exception {
        Long idToDelete = 99L;

        mockMvc.perform(get("/admin/product/delete/{id}", idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"));

        verify(productRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    @DisplayName("Debe mostrar la lista de todas las órdenes")
    void testManageOrders() throws Exception {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-orders"))
                .andExpect(model().attributeExists("orders"));

        verify(orderRepository, times(1)).findAll();
    }
}
