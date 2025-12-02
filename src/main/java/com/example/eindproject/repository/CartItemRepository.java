package com.example.eindproject.repository;

import com.example.eindproject.model.CarItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CarItem, Long>{
}
