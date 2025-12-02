package com.example.eindproject.repository;

import com.example.eindproject.model.CartItem;
import com.example.eindproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByUserAndOrderIsNull(User user);

    Optional<CartItem> findByUserAndOrderIsNullAndProduct_Id(User user, Long productId);

    void deleteByUserAndOrderIsNull(User user);
}
