package org.example.springbootrestapi.service;

import org.example.springbootrestapi.dto.CategoryDto;
import org.example.springbootrestapi.entity.CategoryEntity;
import org.example.springbootrestapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public CategoryDto getCategoryById(int id) {
        return categoryRepository.findById(id)
                .map(CategoryDto::fromCategory)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public int addCategory(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryDto.name());
        category.setSymbol(categoryDto.symbol());
        category.setDescription(categoryDto.description());
        categoryRepository.save(category);
        return category.getId();
    }
}
