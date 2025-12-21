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

        if (quantity <= 0) {
            throw new IllegalArgumentException("Aantal moet minstens 1 zijn.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + productId));

        if (product.getStock() < quantity) {
            throw new IllegalStateException("Niet genoeg stock beschikbaar.");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        var existingOpt = cartItemRepository
                .findByUserAndOrderIsNullAndProduct_Id(user, productId);

        if (existingOpt.isPresent()) {
            CartItem existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setRentalDays(rentalDays);
            cartItemRepository.save(existing);
            return;
        }

        CartItem newItem = new CartItem(user, product, quantity, rentalDays);
        cartItemRepository.save(newItem);
    }

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUserAndOrderIsNull(user);
    }

    public BigDecimal getCartTotal(User user) {
        return getCartItems(user).stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getRentalDays()))
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void removeItem(User user, Long cartItemId) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {

                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);

                cartItemRepository.delete(item);
            }
        });
    }

    public void clearCart(User user) {
        List<CartItem> items = cartItemRepository.findByUserAndOrderIsNull(user);

        for (CartItem item : items) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        cartItemRepository.deleteByUserAndOrderIsNull(user);
    }

    public void updateQuantity(User user, Long cartItemId, int quantity) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {

                Product product = item.getProduct();
                int oldQty = item.getQuantity();

                if (quantity <= 0) {
                    product.setStock(product.getStock() + oldQty);
                    productRepository.save(product);
                    cartItemRepository.delete(item);
                    return;
                }

                int diff = quantity - oldQty;

                if (diff > 0) {
                    if (product.getStock() < diff) {
                        throw new IllegalStateException("Niet genoeg stock beschikbaar voor deze wijziging.");
                    }
                    product.setStock(product.getStock() - diff);
                } else if (diff < 0) {
                    product.setStock(product.getStock() + Math.abs(diff));
                }

                productRepository.save(product);

                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
        });
    }
}
