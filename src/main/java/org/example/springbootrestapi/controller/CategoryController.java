package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.dto.CategoryDto;
import org.example.springbootrestapi.exception.CategoryAlreadyExistsException;
import org.example.springbootrestapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/categories/{name}")
    public CategoryDto getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDto categoryDto) {
        if (categoryService.categoryExists(categoryDto.name())) {
            throw new CategoryAlreadyExistsException("Category with name " + categoryDto.name() + " already exists");
        }
        int id = categoryService.addCategory(categoryDto);
        return ResponseEntity.created(URI.create("/categories/" + id)).build();
    }
}
