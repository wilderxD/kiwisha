package com.kiwisha.controller;

import com.kiwisha.model.CartItem;
import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/agregar")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        Product product = productRepository.findById(productId).orElseThrow();

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                if (item.getQuantity() < product.getStock()) {
                    item.setQuantity(item.getQuantity() + 1);
                }
                found = true;
                break;
            }
        }

        if (!found && product.getStock() > 0) {
            cart.add(new CartItem(product, 1));
        }

        return "redirect:/productos?added=true";
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);
        Double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/actualizar")
    public String updateQuantity(@RequestParam Long productId, @RequestParam Integer quantity, HttpSession session) {
        List<CartItem> cart = getCart(session);
        Product product = productRepository.findById(productId).orElseThrow();

        if (quantity > 0 && quantity <= product.getStock()) {
            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        } else if (quantity <= 0) {
            cart.removeIf(item -> item.getProduct().getId().equals(productId));
        }

        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{id}")
    public String removeItem(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId().equals(id));
        return "redirect:/carrito";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);
        if (cart.isEmpty()) return "redirect:/carrito";
        
        Double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String address, 
                                  @RequestParam String phone,
                                  HttpSession session, 
                                  Model model) {
        List<CartItem> cart = getCart(session);
        Double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();
        
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        
        // Simular envío - Vaciar carrito tras resumen
        session.setAttribute("lastOrder", new ArrayList<>(cart));
        session.setAttribute("lastTotal", total);
        cart.clear();
        
        return "summary";
    }
}
