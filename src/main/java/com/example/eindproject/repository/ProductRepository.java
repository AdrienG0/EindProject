package com.example.eindproject.repository;

import com.example.eindproject.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Id(Long categoryId, Sort sort);

    List<Product> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Product> findByCategory_IdAndNameContainingIgnoreCase(Long categoryId, String name, Sort sort);
}
