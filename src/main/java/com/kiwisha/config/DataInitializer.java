package com.kiwisha.config;

import com.kiwisha.model.Product;
import com.kiwisha.model.User;
import com.kiwisha.model.Coupon;
import com.kiwisha.repository.ProductRepository;
import com.kiwisha.repository.UserRepository;
import com.kiwisha.repository.CouponRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepo, UserRepository userRepo, CouponRepository couponRepo) {
        return args -> {
            // Usuarios
            if (userRepo.count() == 0) {
                userRepo.save(new User(null, "admin", "admin@kiwisha.com", "123", "ADMIN"));
                userRepo.save(new User(null, "juan", "juan@gmail.com", "123", "CLIENTE"));
            }

            // Cupones
            if (couponRepo.count() == 0) {
                couponRepo.save(new Coupon(null, "KIWISHA2026", 20.0, true));
                couponRepo.save(new Coupon(null, "PRODUCTOESTRELLA", 10.0, true));
            }

            // Productos
            if (productRepo.count() == 0) {
                productRepo.saveAll(Arrays.asList(
                    new Product(null, "🌾 Estratega Kiwicha", "Granos de kiwicha premium seleccionados.", 15.50, 100, "Granos", "https://images.unsplash.com/photo-1563820986714-35805f63d763?q=80&w=400"),
                    new Product(null, "🍞 Harina de Kiwicha", "Harina fina ideal para postres nutritivos.", 12.00, 50, "Harinas", "https://images.unsplash.com/photo-1509440159596-0249088772ff?q=80&w=400"),
                    new Product(null, "🍫 Barra Energética", "Barra rústica con kiwicha y miel.", 3.50, 200, "Snacks", "https://images.unsplash.com/photo-1590080875515-8a03b144b5a1?q=80&w=400"),
                    new Product(null, "🥣 Cereal Kiwicha Pop", "Cereal inflado natural, sin azúcar añadida.", 8.90, 80, "Cereales", "https://images.unsplash.com/photo-1596501048549-31764ee9d23f?q=80&w=400")
                ));
            }
        };
    }
}
