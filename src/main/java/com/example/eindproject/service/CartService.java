package com.example.eindproject.service;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.Product;
import com.example.eindproject.model.User;
import com.example.eindproject.repository.CartItemRepository;
import com.example.eindproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addToCart(User user, Long productId, int quantity, int rentalDays) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity moet groter zijn dan 0");
        }

        Product product = productRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + productId));

        int currentStock = (product.getStock() == null ? 0 : product.getStock());

        if (currentStock < quantity) {
            throw new IllegalArgumentException("Niet genoeg stock voor dit product.");
        }

        product.setStock(currentStock - quantity);
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

    @Transactional
    public void removeItem(User user, Long cartItemId) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {

                Product product = productRepository.findByIdForUpdate(item.getProduct().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + item.getProduct().getId()));

                int currentStock = (product.getStock() == null ? 0 : product.getStock());
                product.setStock(currentStock + item.getQuantity());
                productRepository.save(product);

                cartItemRepository.delete(item);
            }
        });
    }

    @Transactional
    public void clearCart(User user) {
        List<CartItem> items = cartItemRepository.findByUserAndOrderIsNull(user);

        for (CartItem item : items) {
            Product product = productRepository.findByIdForUpdate(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + item.getProduct().getId()));

            int currentStock = (product.getStock() == null ? 0 : product.getStock());
            product.setStock(currentStock + item.getQuantity());
            productRepository.save(product);
        }

        cartItemRepository.deleteAll(items);
    }

    @Transactional
    public void updateQuantity(User user, Long cartItemId, int newQuantity) {
        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId()) && item.getOrder() == null) {

                int oldQuantity = item.getQuantity();

                Product product = productRepository.findByIdForUpdate(item.getProduct().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Product bestaat niet: " + item.getProduct().getId()));

                if (newQuantity <= 0) {
                    int currentStock = (product.getStock() == null ? 0 : product.getStock());
                    product.setStock(currentStock + oldQuantity);
                    productRepository.save(product);

                    cartItemRepository.delete(item);
                    return;
                }

                int diff = newQuantity - oldQuantity;

                if (diff > 0) {
                    int currentStock = (product.getStock() == null ? 0 : product.getStock());

                    if (currentStock < diff) {
                        throw new IllegalArgumentException("Niet genoeg stock om quantity te verhogen.");
                    }

                    product.setStock(currentStock - diff);
                    productRepository.save(product);
                }

                if (diff < 0) {
                    int currentStock = (product.getStock() == null ? 0 : product.getStock());
                    product.setStock(currentStock + Math.abs(diff));
                    productRepository.save(product);
                }

                item.setQuantity(newQuantity);
                cartItemRepository.save(item);
            }
        });
    }
}
