package org.example.springbootrestapi.category.valueobject;

import org.example.springbootrestapi.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;


public interface CategoryRepository extends ListCrudRepository<CategoryEntity, Integer> {

}
