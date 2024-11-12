package org.example.springbootrestapi.valueobject;

import org.example.springbootrestapi.dto.CategoryDto;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromCategory)
                .toList();
    }
}
