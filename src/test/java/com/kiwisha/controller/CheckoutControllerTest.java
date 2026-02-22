package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.model.User;
import com.kiwisha.service.CartService;
import com.kiwisha.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private OrderService orderService;

    private MockHttpSession session;
    private User mockUser;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        mockUser = new User();
        mockUser.setEmail("wilder@kiwisha.com");
    }

    
    @Test
    @DisplayName("Debe redirigir a login si no hay usuario en sesión al ir a checkout")
    void testCheckoutNoSession() throws Exception {
        mockMvc.perform(get("/checkout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Debe mostrar checkout si el usuario está autenticado")
    void testCheckoutWithSession() throws Exception {
        session.setAttribute("user", mockUser);
        when(cartService.getItems()).thenReturn(Collections.emptyList());
        when(cartService.getTotal()).thenReturn(0.0);

        mockMvc.perform(get("/checkout").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"))
                .andExpect(model().attributeExists("items", "total"));
    }

   

    @Test
    @DisplayName("Debe fallar proceso de orden si la sesión expira")
    void testProcessOrderNoUser() throws Exception {
        mockMvc.perform(post("/checkout/process"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        
        verifyNoInteractions(orderService);
    }

   
    @Test
    @DisplayName("Debe mostrar el historial si el usuario existe")
    void testHistorySuccess() throws Exception {
        session.setAttribute("user", mockUser);
        when(orderService.getOrdersByClient(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders/history").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("order-history"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @DisplayName("Debe redirigir a login al intentar ver historial sin sesión")
    void testHistoryNoUser() throws Exception {
        mockMvc.perform(get("/orders/history"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}