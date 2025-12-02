package com.example.eindproject.config;

import com.example.eindproject.model.*;
import com.example.eindproject.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            User user = new User("Adrien Student", "adrien@student.ehb.be");
            user.setPassword(passwordEncoder.encode("Password123"));
            user.setRole("USER");
            userRepository.save(user);

            Category cat1 = new Category("Boeken");
            Category cat2 = new Category("Electronica");
            categoryRepository.save(cat1);
            categoryRepository.save(cat2);

            Product p1 = new Product("Spring in Action",
                    "Java Spring Boot book",
                    new BigDecimal("39.99"),
                    cat1);

            Product p2 = new Product("Draadloze muis",
                    "Simpele draadlooze muis",
                    new BigDecimal("19.99"),
                    cat2);

            productRepository.save(p1);
            productRepository.save(p2);

            Order order = new Order(user);
            orderRepository.save(order);

            CarItem item1 = new CarItem(order, p1, 1);
            CarItem item2 = new CarItem(order, p2, 2);

            cartItemRepository.save(item1);
            cartItemRepository.save(item2);

            System.out.println("==== All products from database ====");
            productRepository.findAll().forEach(System.out::println);
            System.out.println("====================================");
        };
    }
}
