package com.example.eindproject.controller;

import com.example.eindproject.model.Product;
import com.example.eindproject.model.Category;
import com.example.eindproject.repository.ProductRepository;
import com.example.eindproject.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            @RequestParam(required = false) String sort,
            Model model,
            @ModelAttribute("cartError") String cartError,
            @ModelAttribute("cartSuccess") String cartSuccess
    ) {

        List<Category> categories = categoryRepository.findAll();

        boolean hasCategoryFilter = (categoryId != null);
        boolean hasSearchFilter = (search != null && !search.trim().isEmpty());

        String cleanedSearch = (search == null) ? "" : search.trim();
        String cleanedSort = (sort == null) ? "" : sort.trim();

        Sort sortObj = Sort.by("name").ascending();

        if (!cleanedSort.isEmpty()) {
            switch (cleanedSort) {
                case "nameAsc":
                    sortObj = Sort.by("name").ascending();
                    break;
                case "nameDesc":
                    sortObj = Sort.by("name").descending();
                    break;
                case "priceAsc":
                    sortObj = Sort.by("price").ascending();
                    break;
                case "priceDesc":
                    sortObj = Sort.by("price").descending();
                    break;
                default:
                    sortObj = Sort.by("name").ascending();
                    cleanedSort = "";
                    break;
            }
        }

        List<Product> products;

        if (hasCategoryFilter && hasSearchFilter) {
            products = productRepository
                    .findByCategory_IdAndNameContainingIgnoreCase(categoryId, cleanedSearch, sortObj);
        } else if (hasCategoryFilter) {
            products = productRepository.findByCategory_Id(categoryId, sortObj);
        } else if (hasSearchFilter) {
            products = productRepository.findByNameContainingIgnoreCase(cleanedSearch, sortObj);
        } else {
            products = productRepository.findAll(sortObj);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("search", cleanedSearch);
        model.addAttribute("sort", cleanedSort);


        if (cartError != null && !cartError.isBlank()) {
            model.addAttribute("cartError", cartError);
            model.addAttribute("stockError", cartError);
        }
        if (cartSuccess != null && !cartSuccess.isBlank()) {
            model.addAttribute("cartSuccess", cartSuccess);
            model.addAttribute("stockSuccess", cartSuccess);
        }

        return "catalog";
    }
}
