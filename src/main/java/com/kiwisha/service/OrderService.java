package com.kiwisha.service;

import com.kiwisha.model.Order;
import com.kiwisha.model.OrderItem;
import com.kiwisha.model.Product;
import com.kiwisha.repository.OrderRepository;
import com.kiwisha.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(String clientEmail, List<OrderItem> items, double total) {
        Order order = new Order();
        order.setClientEmail(clientEmail);
        order.setItems(items);
        order.setTotal(total);
        order.setStatus("PENDIENTE");
        order.setOrderDate(LocalDateTime.now());
        
        // Update stock
        for (OrderItem item : items) {
            Product p = item.getProduct();
            p.setStock(p.getStock() - item.getQuantity());
            productRepository.save(p);
            item.setOrder(order);
        }
        
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByClient(String email) {
        return orderRepository.findByClientEmail(email);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updateStatus(Long orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
