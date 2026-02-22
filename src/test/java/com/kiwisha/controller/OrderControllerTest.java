package com.kiwisha.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe obtener el historial de órdenes (Cubre constructor y GetMapping)")
    void testGetHistory() throws Exception {
        // Al llamar a este endpoint, JaCoCo marcará el constructor y la lista 'orders'
        mockMvc.perform(get("/api/orders/history/{client}", "Juan Perez"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("En camino")))
                .andExpect(jsonPath("$[0].client", is("Juan Perez")));
    }

    @Test
    @DisplayName("Debe actualizar el estado de la orden (Cubre PatchMapping)")
    void testUpdateStatus() throws Exception {
        // Preparamos el JSON que espera el @RequestBody Map<String, String>
        String jsonPayload = "{\"status\": \"Entregado\"}";

        mockMvc.perform(patch("/api/orders/{id}/status", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Estado actualizado a: Entregado"));
    }
}
