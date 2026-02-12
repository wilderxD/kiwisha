package com.kiwisha.controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private List<Map<String, Object>> orders = new ArrayList<>();

    public OrderController() {
        orders.add(Map.of("id", 1, "status", "En camino", "total", 45.0, "client", "Juan Perez"));
    }

    @GetMapping("/history/{client}")
    public List<Map<String, Object>> getHistory(@PathVariable String client) {
        return orders; // Simplificado
    }

    @PatchMapping("/{id}/status")
    public String updateStatus(@PathVariable int id, @RequestBody Map<String, String> payload) {
        return "Estado actualizado a: " + payload.get("status");
    }
}
