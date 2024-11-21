package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.dto.CategoryDto;
import org.example.springbootrestapi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryDto getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/categories")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDto categoryDto) {
        int id = categoryService.addCategory(categoryDto);
        return ResponseEntity.created(URI.create("/categories/" + id)).build();
    }


}
