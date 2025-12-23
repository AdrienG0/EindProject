package com.example.eindproject.controller;

import com.example.eindproject.model.Order;
import com.example.eindproject.model.User;
import com.example.eindproject.repository.OrderRepository;
import com.example.eindproject.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public HomeController(OrderRepository orderRepository,
                          UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/mijn-reservaties")
    public String mijnReservaties(Authentication authentication, Model model) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));

        List<Order> orders = orderRepository.findByUserEmailOrderByIdDesc(email);

        model.addAttribute("orders", orders);

        return "mijn-reservaties";
    }
}
