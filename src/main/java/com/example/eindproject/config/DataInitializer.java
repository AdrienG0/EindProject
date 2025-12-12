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

            User admin = new User("Admin", "admin@ehb.be");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            Category cat1 = new Category("Boeken");
            Category cat2 = new Category("Electronica");
            Category cat3 = new Category("Gaming accessoires");
            Category cat4 = new Category("Lampen");
            Category cat5 = new Category("Kabels");

            categoryRepository.save(cat1);
            categoryRepository.save(cat2);
            categoryRepository.save(cat3);
            categoryRepository.save(cat4);
            categoryRepository.save(cat5);

            Product p1 = new Product(
                    "Spring in Action",
                    "Java Spring Boot book",
                    new BigDecimal("4.99"),
                    cat1
            );

            Product p2 = new Product(
                    "Draadloze muis",
                    "Simpele draadloze muis",
                    new BigDecimal("3.00"),
                    cat2
            );

            Product g1 = new Product(
                    "Logitech G Pro X Superlight 2",
                    "Ultralichte draadloze gamingmuis met hoge precisie voor competitieve gamers.",
                    new BigDecimal("19.99"),
                    cat3
            );

            Product g2 = new Product(
                    "SteelSeries Arctis Nova 7 Wireless",
                    "Comfortabele draadloze gamingheadset met helder geluid en lange batterijduur.",
                    new BigDecimal("29.99"),
                    cat3
            );

            Product g3 = new Product(
                    "Razer Huntsman Mini",
                    "Compact 60% mechanisch toetsenbord met optische switches voor snelle respons.",
                    new BigDecimal("14.99"),
                    cat3
            );

            Product g4 = new Product(
                    "Corsair MM700 RGB Muismat",
                    "Extra grote RGB-muismat met USB-hub, ideaal voor volledige setup.",
                    new BigDecimal("8.99"),
                    cat3
            );

            Product g5 = new Product(
                    "Elgato Stream Deck MK.2",
                    "Programmeerbaar streamdeck voor snelle bediening van scenes, macro’s en acties.",
                    new BigDecimal("24.99"),
                    cat3
            );

            Product g6 = new Product(
                    "Gaming Headset",
                    "Simpele gaming headset met hoge geluidskwaliteit",
                    new BigDecimal("12.99"),
                    cat3
            );

            Product l1 = new Product(
                    "Bureau LED Lamp",
                    "Energiezuinige LED bureaulamp met instelbare helderheid.",
                    new BigDecimal("9.99"),
                    cat4
            );

            Product l2 = new Product(
                    "RGB Gaming Lamp",
                    "Sfeervolle RGB lamp ideaal voor een gaming setup.",
                    new BigDecimal("11.99"),
                    cat4
            );

            Product k1 = new Product(
                    "USB-C Oplaadkabel",
                    "Snelle USB-C kabel voor opladen en dataoverdracht.",
                    new BigDecimal("2.99"),
                    cat5
            );

            Product k2 = new Product(
                    "HDMI Kabel 2m",
                    "HDMI kabel geschikt voor 4K beeldkwaliteit.",
                    new BigDecimal("4.49"),
                    cat5
            );

            Product k3 = new Product(
                    "Ethernet Kabel Cat6",
                    "Snelle netwerkkabel voor stabiele internetverbinding.",
                    new BigDecimal("3.99"),
                    cat5
            );

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(g1);
            productRepository.save(g2);
            productRepository.save(g3);
            productRepository.save(g4);
            productRepository.save(g5);
            productRepository.save(g6);
            productRepository.save(l1);
            productRepository.save(l2);
            productRepository.save(k1);
            productRepository.save(k2);
            productRepository.save(k3);
            
            Order order = new Order(user);
            orderRepository.save(order);

            CartItem item1 = new CartItem(user, p1, 1);
            CartItem item2 = new CartItem(user, p2, 2);

            cartItemRepository.save(item1);
            cartItemRepository.save(item2);

            System.out.println("==== All products from database ====");
            productRepository.findAll().forEach(System.out::println);
            System.out.println("====================================");
        };
    }
}
