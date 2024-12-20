package org.example.springbootrestapi.dto;
import org.example.springbootrestapi.entity.CategoryEntity;

public record CategoryDto(String name, String symbol, String description) {
    public static CategoryDto fromCategory(CategoryEntity category) {
        return new CategoryDto(category.getName(), category.getSymbol(), category.getDescription());
    }
}
