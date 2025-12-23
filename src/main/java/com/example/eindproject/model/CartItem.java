package com.example.eindproject.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private int rentalDays;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItem() {
    }

    public CartItem(Order order, Product product, int quantity, int rentalDays) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.rentalDays = rentalDays;
    }

    public CartItem(User user, Product product, int quantity, int rentalDays) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.rentalDays = rentalDays;
    }

    public CartItem(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.rentalDays = 1;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        if (product == null || product.getPrice() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal pricePerDay = product.getPrice();
        BigDecimal days = BigDecimal.valueOf(rentalDays);
        BigDecimal qty = BigDecimal.valueOf(quantity);

        return pricePerDay.multiply(days).multiply(qty);
    }

    @Override
    public String toString() {
        return "CartItem{id=" + id +
                ", quantity=" + quantity +
                ", product=" + (product != null ? product.getName() : null) +
                '}';
    }
}
