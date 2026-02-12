package com.kiwisha.repository;

import com.kiwisha.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserEmail(String userEmail);
    void deleteByUserEmailAndProductId(String userEmail, Long productId);
}
