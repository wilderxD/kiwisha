package com.kiwisha.controller;

import com.kiwisha.model.Review;
import com.kiwisha.model.WishlistItem;
import com.kiwisha.model.Product;
import com.kiwisha.model.User;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.repository.ReviewRepository;
import com.kiwisha.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductActionController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product/review")
    public String addReview(@RequestParam Long productId, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Review review = new Review();
        review.setProductId(productId);
        review.setUsername(user.getUsername());
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
        
        return "redirect:/product/" + productId;
    }

    @PostMapping("/wishlist/add")
    public String addToWishlist(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Product p = productRepository.findById(productId).orElse(null);
        if (p != null) {
            wishlistRepository.save(new WishlistItem(user.getEmail(), p));
        }
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist")
    public String viewWishlist(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("items", wishlistRepository.findByUserEmail(user.getEmail()));
        return "wishlist";
    }
}
