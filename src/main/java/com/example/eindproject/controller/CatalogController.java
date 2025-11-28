package com.example.eindproject.controller;

import com.example.eindproject.model.Product;
import com.example.eindproject.model.Category;
import com.example.eindproject.repository.ProductRepository;
import com.example.eindproject.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CatalogController(ProductRepository productRepository,
                             CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/catalog")
    public String showCatalog(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            Model model
    ) {

        List<Category> categories = categoryRepository.findAll();

        List<Product> products;

        boolean hasCategoryFilter = (categoryId != null);
        boolean hasSearchFilter = (search != null && !search.trim().isEmpty());

        if (hasCategoryFilter && hasSearchFilter) {
            products = productRepository
                    .findByCategory_IdAndNameContainingIgnoreCase(categoryId, search.trim());
        } else if (hasCategoryFilter) {
            products = productRepository.findByCategory_Id(categoryId);
        } else if (hasSearchFilter) {
            products = productRepository.findByNameContainingIgnoreCase(search.trim());
        } else {
            products = productRepository.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("search", search == null ? "" : search);

        return "catalog";
    }
}
