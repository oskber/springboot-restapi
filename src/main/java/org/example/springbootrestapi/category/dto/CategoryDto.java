package org.example.springbootrestapi.category.dto;
import org.example.springbootrestapi.entity.CategoryEntity;

public record CategoryDto(String name, Character symbol, String description) {
    public static CategoryDto fromCategory(CategoryEntity category) {
        return new CategoryDto(category.getName(), category.getSymbol(), category.getDescription());
    }
}
