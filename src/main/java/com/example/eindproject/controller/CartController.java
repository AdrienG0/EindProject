package com.example.eindproject.controller;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.User;
import com.example.eindproject.model.Order;
import com.example.eindproject.service.CartService;
import com.example.eindproject.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.eindproject.repository.OrderRepository;
import com.example.eindproject.repository.CartItemRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public CartController(CartService cartService,
                          UserRepository userRepository,
                          OrderRepository orderRepository,
                          CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
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

        return "cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId,
                                 @RequestParam("quantity") int quantity,
                                 Authentication authentication) {

        User user = getCurrentUser(authentication);
        cartService.updateQuantity(user, cartItemId, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);

        var items = cartService.getCartItems(user);

        if (items.isEmpty()) {
            return "redirect:/cart";
        }

        var total = cartService.getCartTotal(user);

        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String confirmCheckout(Authentication authentication, Model model) {
        User user = getCurrentUser(authentication);

        var items = cartService.getCartItems(user);

        if (items.isEmpty()) {
            return "redirect:/cart";
        }

        var total = cartService.getCartTotal(user);

        Order order = new Order();

        order = orderRepository.save(order);

        for (CartItem item : items) {
            item.setOrder(order);
        }
        cartItemRepository.saveAll(items);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        model.addAttribute("total", total);

        return "confirmation";
    }
}
