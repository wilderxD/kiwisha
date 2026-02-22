package com.kiwisha.service;

import com.kiwisha.model.Order;
import com.kiwisha.model.OrderItem;
import com.kiwisha.model.Product;
import com.kiwisha.repository.OrderRepository;
import com.kiwisha.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Debe crear una orden y actualizar el stock de los productos")
    void testCreateOrder() {
        // Preparar datos de prueba
        Product kiwichaPop = new Product();
        kiwichaPop.setId(1L);
        kiwichaPop.setStock(10);

        OrderItem item = new OrderItem();
        item.setProduct(kiwichaPop);
        item.setQuantity(2);

        List<OrderItem> items = Arrays.asList(item);
        String email = "cliente@kiwisha.pe";

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        // Ejecutar
        Order result = orderService.createOrder(email, items, 50.0);

        // Verificar
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getStatus());
        assertEquals(8, kiwichaPop.getStock()); // 10 - 2
        assertEquals(result, item.getOrder()); // Verifica la relación bidireccional
        
        verify(productRepository, times(1)).save(kiwichaPop);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Debe obtener órdenes por email de cliente")
    void testGetOrdersByClient() {
        String email = "wilder@test.com";
        orderService.getOrdersByClient(email);
        verify(orderRepository).findByClientEmail(email);
    }

    @Test
    @DisplayName("Debe obtener todas las órdenes")
    void testGetAllOrders() {
        orderService.getAllOrders();
        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("Debe actualizar el estado si la orden existe (Rama ifPresent)")
    void testUpdateStatusFound() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDIENTE");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.updateStatus(1L, "ENTREGADO");

        assertEquals("ENTREGADO", order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("No debe hacer nada si la orden no existe (Rama empty)")
    void testUpdateStatusNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        orderService.updateStatus(1L, "ENTREGADO");

        verify(orderRepository, never()).save(any(Order.class));
    }
}
