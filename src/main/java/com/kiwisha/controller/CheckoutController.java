package com.kiwisha.controller;

import com.kiwisha.model.OrderItem;
import com.kiwisha.model.User;
import com.kiwisha.service.CartService;
import com.kiwisha.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "checkout";
    }

    @PostMapping("/checkout/process")
    public String processOrder(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<OrderItem> orderItems = cartService.getItems().stream()
                .map(cartItem -> {
                    OrderItem oi = new OrderItem();
                    oi.setProduct(cartItem.getProduct());
                    oi.setQuantity(cartItem.getQuantity());
                    oi.setPrice(cartItem.getProduct().getPrice());
                    return oi;
                }).collect(Collectors.toList());

        orderService.createOrder(user.getEmail(), orderItems, cartService.getTotal());
        cartService.clear();
        return "redirect:/orders/history";
    }

    @GetMapping("/orders/history")
    public String history(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("orders", orderService.getOrdersByClient(user.getEmail()));
        return "order-history";
    }
}
