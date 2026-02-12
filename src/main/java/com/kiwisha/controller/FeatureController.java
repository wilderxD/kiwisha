package com.kiwisha.controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    private List<Map<String, Object>> reviews = new ArrayList<>();
    private List<String> wishlist = new ArrayList<>();

    @PostMapping("/reviews")
    public String addReview(@RequestBody Map<String, Object> review) {
        reviews.add(review);
        return "Reseña guardada con éxito.";
    }

    @PostMapping("/wishlist")
    public String addToWishlist(@RequestBody Map<String, String> item) {
        wishlist.add(item.get("sku"));
        return "Agregado a lista de deseos.";
    }

    @GetMapping("/coupons/validate/{code}")
    public Map<String, Object> validateCoupon(@PathVariable String code) {
        if ("KIWISHA2026".equals(code)) {
            return Map.of("valid", true, "discount", 15.0);
        }
        return Map.of("valid", false, "discount", 0);
    }
}
