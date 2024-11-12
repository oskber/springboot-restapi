package org.example.springbootrestapi.valueobject;

import org.example.springbootrestapi.dto.CategoryDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
