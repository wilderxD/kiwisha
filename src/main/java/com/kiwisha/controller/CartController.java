package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @PostMapping("/agregar")
    public String addToCart(@RequestParam Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        if (product.getStock() > 0) {
            cartService.addProduct(product);
        }
        return "redirect:/productos?added=true";
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart";
    }

    @PostMapping("/actualizar")
    public String updateQuantity(@RequestParam Long productId, @RequestParam Integer quantity) {
        if (quantity <= 0) {
            cartService.removeProduct(productId);
        } else {
            // Logic to cap quantity by stock could be here or in service
            cartService.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .forEach(i -> i.setQuantity(quantity));
        }
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{id}")
    public String removeItem(@PathVariable Long id) {
        cartService.removeProduct(id);
        return "redirect:/carrito";
    }
}
