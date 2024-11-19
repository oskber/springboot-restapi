package org.example.springbootrestapi.category.valueobject;

import org.example.springbootrestapi.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;


public interface CategoryRepository extends ListCrudRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
}
