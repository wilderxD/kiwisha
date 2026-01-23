package com.kiwisha.config;

import com.kiwisha.model.Product;
import com.kiwisha.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            repository.saveAll(Arrays.asList(
                new Product(null, "Estratega Kiwicha", "Granos de kiwicha premium seleccionados.", 15.50, 100, "Granos", "https://via.placeholder.com/150"),
                new Product(null, "Harina de Kiwicha", "Harina fina ideal para postres nutritivos.", 12.00, 50, "Harinas", "https://via.placeholder.com/150"),
                new Product(null, "Barra Energética", "Barra rústica con kiwicha y miel.", 3.50, 200, "Snacks", "https://via.placeholder.com/150"),
                new Product(null, "Cereal Kiwicha Pop", "Cereal inflado natural, sin azúcar añadida.", 8.90, 80, "Cereales", "https://via.placeholder.com/150")
            ));
        };
    }
}
