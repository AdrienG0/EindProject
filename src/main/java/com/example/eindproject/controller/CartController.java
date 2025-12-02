package com.example.eindproject.controller;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.User;
import com.example.eindproject.service.CartService;
import com.example.eindproject.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService,
                          UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // principal = email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Authentication authentication) {

        User user = getCurrentUser(authentication);
        cartService.addToCart(user, productId, quantity);

        return "redirect:/catalog";
    }

    @GetMapping
    public String showCart(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);

        List<CartItem> items = cartService.getCartItems(user);
        BigDecimal total = cartService.getCartTotal(user);

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "cart"; // => cart.html (maken we in de volgende stap mooi)
    }
}
