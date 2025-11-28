package com.example.eindproject.repository;

import com.example.eindproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory_IdAndNameContainingIgnoreCase(Long categoryId, String name);
}

// Dit repository geeft extra zoekfuncties voor Product:
// - findByCategory_Id: haalt alle producten op die in een bepaalde categorie zitten
// - findByNameContainingIgnoreCase: zoekt producten op naam, zonder hoofdlettergevoeligheid
// - findByCategory_IdAndNameContainingIgnoreCase: combinatie van categorie + zoekterm
// Spring maakt deze queries automatisch aan op basis van de methodenamen.
