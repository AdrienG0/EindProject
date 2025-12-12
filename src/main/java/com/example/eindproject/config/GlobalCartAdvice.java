package com.example.eindproject.config;

import com.example.eindproject.model.User;
import com.example.eindproject.repository.UserRepository;
import com.example.eindproject.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GlobalCartAdvice {

    private final CartService cartService;
    private final UserRepository userRepository;

    public GlobalCartAdvice(CartService cartService,
                            UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @ModelAttribute("cartCount")
    public int cartCount(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return 0;
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return 0;
        }

        return cartService.getCartItems(user).size();
    }
}
