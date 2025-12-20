package com.example.eindproject.config;

import com.example.eindproject.model.*;
import com.example.eindproject.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.user.password}")
    private String userPassword;

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
            
            if (userRepository.findByEmail("adrien@student.ehb.be").isEmpty()) {
                User user = new User("Adrien Student", "adrien@student.ehb.be");
                user.setPassword(passwordEncoder.encode(userPassword));
                user.setRole("USER");
                userRepository.save(user);
            }

            if (userRepository.findByEmail("admin@ehb.be").isEmpty()) {
                User admin = new User("Admin", "admin@ehb.be");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            if (categoryRepository.count() == 0) {

                Category catLampen = new Category("Lampen");
                Category catLichtpanelen = new Category("Lichtpanelen");
                Category catPodium = new Category("Podiumelementen");
                Category catKabels = new Category("Kabels & adapters");
                Category catAudio = new Category("Audio");
                Category catStroom = new Category("Stroom & verdelers");
                Category catStatieven = new Category("Statieven & bevestiging");

                categoryRepository.save(catLampen);
                categoryRepository.save(catLichtpanelen);
                categoryRepository.save(catPodium);
                categoryRepository.save(catKabels);
                categoryRepository.save(catAudio);
                categoryRepository.save(catStroom);
                categoryRepository.save(catStatieven);

                if (productRepository.count() == 0) {

                    Product l1 = new Product(
                            "LED Fresnel 150W",
                            "Sterke LED Fresnel met focusknop. Ideaal voor podium- en studioverlichting.",
                            new BigDecimal("12.00"),
                            catLampen
                    );

                    Product l2 = new Product(
                            "LED PAR 64 RGB",
                            "Kleurverlichting met RGB. Geschikt voor sfeerlicht op events en optredens.",
                            new BigDecimal("8.50"),
                            catLampen
                    );

                    Product l3 = new Product(
                            "Spotlight 50W (warm wit)",
                            "Compacte spotlamp voor accentverlichting op podium of decor.",
                            new BigDecimal("6.00"),
                            catLampen
                    );

                    Product l4 = new Product(
                            "DMX Moving Head (basis)",
                            "Bewegende koplamp met DMX-sturing voor eenvoudige lichtshows.",
                            new BigDecimal("18.00"),
                            catLampen
                    );

                    Product l5 = new Product(
                            "Theaterlamp Halogeen (klassiek)",
                            "Klassieke theaterlamp voor basis podiumlicht (waar beschikbaar).",
                            new BigDecimal("7.50"),
                            catLampen
                    );

                    Product lp1 = new Product(
                            "LED Lichtpaneel 1x1 (bi-color)",
                            "Groot lichtpaneel met instelbare kleurtemperatuur. Handig voor opnames en interviews.",
                            new BigDecimal("14.00"),
                            catLichtpanelen
                    );

                    Product lp2 = new Product(
                            "Softbox voor lichtpaneel",
                            "Diffuser/softbox om het licht zachter te maken (minder schaduwen).",
                            new BigDecimal("4.00"),
                            catLichtpanelen
                    );

                    Product lp3 = new Product(
                            "Reflectiescherm (5-in-1)",
                            "Opvouwbaar reflectiescherm voor lichtcontrole bij video en foto.",
                            new BigDecimal("3.50"),
                            catLichtpanelen
                    );

                    Product lp4 = new Product(
                            "LED Buislamp (RGB)",
                            "RGB buislamp voor creatieve verlichting of achtergrondlicht.",
                            new BigDecimal("9.00"),
                            catLichtpanelen
                    );

                    Product p1 = new Product(
                            "Podiumdeel 2x1m",
                            "Modulair podiumdeel (2m x 1m) voor tijdelijke podiumopbouw.",
                            new BigDecimal("25.00"),
                            catPodium
                    );

                    Product p2 = new Product(
                            "Podiumtrap (2 treden)",
                            "Trap voor veilige toegang tot podium.",
                            new BigDecimal("10.00"),
                            catPodium
                    );

                    Product p3 = new Product(
                            "Podiumrok / afwerking (zwart)",
                            "Zwarte podiumrok om kabels en constructie netjes weg te werken.",
                            new BigDecimal("6.00"),
                            catPodium
                    );

                    Product p4 = new Product(
                            "Achtergronddoek (zwart) 3m",
                            "Zwart doek voor achtergrond of afscherming.",
                            new BigDecimal("8.00"),
                            catPodium
                    );

                    Product p5 = new Product(
                            "Afzetpaaltjes + koord (set)",
                            "Set voor afbakening of publieksgeleiding.",
                            new BigDecimal("7.00"),
                            catPodium
                    );

                    Product k1 = new Product(
                            "XLR Kabel 10m",
                            "Gebalanceerde audiokabel (microfoon/mixer).",
                            new BigDecimal("2.50"),
                            catKabels
                    );

                    Product k2 = new Product(
                            "XLR Kabel 5m",
                            "Gebalanceerde audiokabel (microfoon/mixer).",
                            new BigDecimal("2.00"),
                            catKabels
                    );

                    Product k3 = new Product(
                            "DMX Kabel 10m",
                            "DMX-kabel voor lichtsturing tussen armaturen.",
                            new BigDecimal("3.00"),
                            catKabels
                    );

                    Product k4 = new Product(
                            "HDMI Kabel 5m",
                            "HDMI kabel voor projector of scherm (tot 5 meter).",
                            new BigDecimal("2.50"),
                            catKabels
                    );

                    Product k5 = new Product(
                            "HDMI Kabel 10m",
                            "HDMI kabel voor projector of scherm (tot 10 meter).",
                            new BigDecimal("3.50"),
                            catKabels
                    );

                    Product k6 = new Product(
                            "Jack 6.35mm naar Jack 6.35mm (3m)",
                            "Audiokabel voor instrumenten of mixer-aansluitingen.",
                            new BigDecimal("1.50"),
                            catKabels
                    );

                    Product k7 = new Product(
                            "Minijack 3.5mm naar 2x RCA",
                            "Adapterkabel voor laptop/telefoon naar mengpaneel.",
                            new BigDecimal("1.50"),
                            catKabels
                    );

                    Product k8 = new Product(
                            "USB-C naar HDMI adapter",
                            "Adapter om laptop via USB-C aan te sluiten op projector/scherm.",
                            new BigDecimal("2.50"),
                            catKabels
                    );

                    Product k9 = new Product(
                            "RCA Kabel (stereo) 5m",
                            "RCA kabel voor audio (rood/wit).",
                            new BigDecimal("1.80"),
                            catKabels
                    );

                    Product k10 = new Product(
                            "Gaffer tape (zwart) 25m",
                            "Sterke tape om kabels veilig vast te zetten (podium-proof).",
                            new BigDecimal("2.00"),
                            catKabels
                    );

                    Product a1 = new Product(
                            "Dynamische microfoon (basis)",
                            "Robuuste microfoon voor spraak en zang (geschikt voor podium).",
                            new BigDecimal("6.50"),
                            catAudio
                    );

                    Product a2 = new Product(
                            "Draadloze microfoonset (2 stuks)",
                            "Set met ontvanger en twee draadloze microfoons voor presentaties.",
                            new BigDecimal("18.00"),
                            catAudio
                    );

                    Product a3 = new Product(
                            "Mengpaneel 8-kanaals",
                            "Compact mengpaneel voor microfoons en audio-bronnen.",
                            new BigDecimal("15.00"),
                            catAudio
                    );

                    Product a4 = new Product(
                            "Actieve speaker 12 inch",
                            "Actieve PA-speaker voor presentaties of kleine events.",
                            new BigDecimal("20.00"),
                            catAudio
                    );

                    Product a5 = new Product(
                            "DI Box (passief)",
                            "DI box voor het proper aansluiten van instrument/laptop op mixer.",
                            new BigDecimal("4.00"),
                            catAudio
                    );

                    Product a6 = new Product(
                            "Koptelefoon (studio)",
                            "Neutrale koptelefoon voor monitoring bij opname of montage.",
                            new BigDecimal("5.00"),
                            catAudio
                    );

                    Product s1 = new Product(
                            "Stekkerblok 6-voudig",
                            "Stekkerdoos met 6 stopcontacten voor lokaal of podium.",
                            new BigDecimal("2.50"),
                            catStroom
                    );

                    Product s2 = new Product(
                            "Haspel 25m",
                            "Kabelhaspel 25 meter (met beveiliging).",
                            new BigDecimal("5.00"),
                            catStroom
                    );

                    Product s3 = new Product(
                            "Haspel 40m",
                            "Kabelhaspel 40 meter (met beveiliging).",
                            new BigDecimal("7.00"),
                            catStroom
                    );

                    Product s4 = new Product(
                            "Power strip met overspanningsbeveiliging",
                            "Stekkerblok met overspanningsbeveiliging voor gevoelige apparatuur.",
                            new BigDecimal("3.00"),
                            catStroom
                    );

                    Product s5 = new Product(
                            "IEC stroomkabel (PC-kabel) 2m",
                            "Stroomkabel voor verschillende toestellen (IEC C13).",
                            new BigDecimal("1.20"),
                            catStroom
                    );

                    Product st1 = new Product(
                            "Microfoonstatief (boom arm)",
                            "Verstelbaar microfoonstatief met boom-arm.",
                            new BigDecimal("4.50"),
                            catStatieven
                    );

                    Product st2 = new Product(
                            "Lichtstatief (2m)",
                            "Lichtstatief voor lampen of lichtpaneel, tot ongeveer 2 meter.",
                            new BigDecimal("5.00"),
                            catStatieven
                    );

                    Product st3 = new Product(
                            "Speaker statief (set van 2)",
                            "Set van 2 statieven om PA-speakers veilig te plaatsen.",
                            new BigDecimal("8.00"),
                            catStatieven
                    );

                    Product st4 = new Product(
                            "Truss clamp (klem) set",
                            "Bevestigingsklemmen voor het monteren van lampen aan truss.",
                            new BigDecimal("3.50"),
                            catStatieven
                    );

                    Product st5 = new Product(
                            "Safety cable (veiligheidskabel)",
                            "Extra beveiliging om lampen veilig op te hangen.",
                            new BigDecimal("2.00"),
                            catStatieven
                    );

                    productRepository.save(l1);
                    productRepository.save(l2);
                    productRepository.save(l3);
                    productRepository.save(l4);
                    productRepository.save(l5);

                    productRepository.save(lp1);
                    productRepository.save(lp2);
                    productRepository.save(lp3);
                    productRepository.save(lp4);

                    productRepository.save(p1);
                    productRepository.save(p2);
                    productRepository.save(p3);
                    productRepository.save(p4);
                    productRepository.save(p5);

                    productRepository.save(k1);
                    productRepository.save(k2);
                    productRepository.save(k3);
                    productRepository.save(k4);
                    productRepository.save(k5);
                    productRepository.save(k6);
                    productRepository.save(k7);
                    productRepository.save(k8);
                    productRepository.save(k9);
                    productRepository.save(k10);

                    productRepository.save(a1);
                    productRepository.save(a2);
                    productRepository.save(a3);
                    productRepository.save(a4);
                    productRepository.save(a5);
                    productRepository.save(a6);

                    productRepository.save(s1);
                    productRepository.save(s2);
                    productRepository.save(s3);
                    productRepository.save(s4);
                    productRepository.save(s5);

                    productRepository.save(st1);
                    productRepository.save(st2);
                    productRepository.save(st3);
                    productRepository.save(st4);
                    productRepository.save(st5);
                }
            }

            if (orderRepository.count() == 0) {
                User user = userRepository.findByEmail("adrien@student.ehb.be").orElse(null);
                if (user != null) {
                    Order order = new Order(user);
                    orderRepository.save(order);
                }
            }

            if (cartItemRepository.count() == 0) {
                User user = userRepository.findByEmail("adrien@student.ehb.be").orElse(null);
                if (user != null && productRepository.count() > 0) {
                    var products = productRepository.findAll();
                    if (products.size() >= 2) {
                        CartItem item1 = new CartItem(user, products.get(0), 1);
                        CartItem item2 = new CartItem(user, products.get(1), 2);
                        cartItemRepository.save(item1);
                        cartItemRepository.save(item2);
                    }
                }
            }

            System.out.println("==== All products from database ====");
            productRepository.findAll().forEach(System.out::println);
            System.out.println("====================================");
        };
    }
}
