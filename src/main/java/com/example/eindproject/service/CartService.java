package com.example.eindproject.service;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.Product;
import com.example.eindproject.model.User;
import com.example.eindproject.repository.CartItemRepository;
import com.example.eindproject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }
    public void addToCart(User user, Long productId, int quantity, int rentalDays) {
        var existingOpt = cartItemRepository
                .findByUserAndOrderIsNullAndProduct_Id(user, productId);

        if (existingOpt.isPresent()) {
            CartItem existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setRentalDays(rentalDays);
            cartItemRepository.save(existing);
            return;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + productId));

        CartItem newItem = new CartItem(user, product, quantity, rentalDays);
        cartItemRepository.save(newItem);
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUserAndOrderIsNull(user);
    }
    public BigDecimal getCartTotal(User user) {
        return getCartItems(user).stream()
                .map(item -> item.getProduct().getPrice()                         // prijs per dag
                        .multiply(BigDecimal.valueOf(item.getRentalDays()))       // × dagen
                        .multiply(BigDecimal.valueOf(item.getQuantity())))        // × aantal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void removeItem(User user, Long cartItemId) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {
                cartItemRepository.delete(item);
            }
        });
    }

    public void clearCart(User user) {
        cartItemRepository.deleteByUserAndOrderIsNull(user);
    }
    public void updateQuantity(User user, Long cartItemId, int quantity) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {

                if (quantity <= 0) {
                    cartItemRepository.delete(item);
                } else {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                }
            }
        });
    }
}
