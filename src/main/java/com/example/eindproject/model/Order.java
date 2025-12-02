package com.example.eindproject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CarItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(User user) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CarItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{id=" + id +
                ", orderDate=" + orderDate +
                ", user=" + (user != null ? user.getName() : null) +
                '}';
    }
}
