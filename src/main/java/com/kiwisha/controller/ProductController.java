package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listProducts(@RequestParam(required = false) String q, 
                               @RequestParam(required = false) String categoria, 
                               Model model) {
        List<Product> products;
        if (q != null && !q.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(q);
        } else if (categoria != null && !categoria.isEmpty()) {
            products = productRepository.findByCategory(categoria);
        } else {
            products = productRepository.findAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("query", q);
        model.addAttribute("category", categoria);
        return "catalog";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        model.addAttribute("product", product);
        return "product-detail";
    }
}
