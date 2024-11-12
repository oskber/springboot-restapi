package org.example.springbootrestapi.dto;
import org.example.springbootrestapi.entity.Category;

public record CategoryDto(String name, Character symbol, String description) {
    public static CategoryDto fromCategory(Category category) {
        return new CategoryDto(category.getName(), category.getSymbol(), category.getDescription());
    }
}
