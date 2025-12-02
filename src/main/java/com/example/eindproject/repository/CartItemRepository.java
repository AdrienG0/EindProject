package com.example.eindproject.repository;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByUserAndOrderIsNull(User user);
}
