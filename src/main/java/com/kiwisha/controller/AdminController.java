package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalSales", 1500.50);
        model.addAttribute("ordersCount", orderRepository.count());
        model.addAttribute("products", productRepository.findAll());
        long lowStockCount = productRepository.findAll().stream()
                .filter(p -> p.getStock() < 5)
                .count();
        model.addAttribute("lowStock", lowStockCount);
        return "admin-dashboard";
    }

    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin-orders";
    }
}
