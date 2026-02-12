package com.kiwisha.repository;

import com.kiwisha.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientEmail(String clientEmail);
}
