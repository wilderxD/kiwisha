package com.kiwisha.controller;

import com.kiwisha.model.Product;
import com.kiwisha.model.User;
import com.kiwisha.model.WishlistItem;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.repository.ReviewRepository;
import com.kiwisha.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductActionController.class)
class ProductActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private WishlistRepository wishlistRepository;

    @MockBean
    private ProductRepository productRepository;

    private MockHttpSession session;
    private User mockUser;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        mockUser = new User();
        mockUser.setUsername("WilderDev");
        mockUser.setEmail("wilder@kiwisha.com");
    }

    // --- TESTS PARA addReview ---

    @Test
    @DisplayName("Debe redirigir a login si el usuario no está en sesión al comentar")
    void testAddReviewNoSession() throws Exception {
        mockMvc.perform(post("/product/review")
                .param("productId", "1")
                .param("rating", "5")
                .param("comment", "Excelente kiwicha"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Debe guardar la reseña si el usuario está autenticado")
    void testAddReviewSuccess() throws Exception {
        session.setAttribute("user", mockUser);

        mockMvc.perform(post("/product/review")
                .session(session)
                .param("productId", "10")
                .param("rating", "4")
                .param("comment", "Muy nutritivo"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/10"));

        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Debe agregar a wishlist si el producto existe")
    void testAddToWishlistSuccess() throws Exception {
        session.setAttribute("user", mockUser);
        Product product = new Product();
        product.setId(1L);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(post("/wishlist/add")
                .session(session)
                .param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistRepository, times(1)).save(any(WishlistItem.class));
    }

    @Test
    @DisplayName("No debe guardar nada si el producto no existe (Branch coverage)")
    void testAddToWishlistProductNotFound() throws Exception {
        session.setAttribute("user", mockUser);
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/wishlist/add")
                .session(session)
                .param("productId", "99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        verify(wishlistRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe redirigir a login al intentar agregar a wishlist sin sesión")
    void testAddToWishlistNoSession() throws Exception {
        mockMvc.perform(post("/wishlist/add").param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Debe mostrar la wishlist del usuario autenticado")
    void testViewWishlistSuccess() throws Exception {
        session.setAttribute("user", mockUser);
        when(wishlistRepository.findByUserEmail(mockUser.getEmail())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/wishlist").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist"))
                .andExpect(model().attributeExists("items"));
    }
}
