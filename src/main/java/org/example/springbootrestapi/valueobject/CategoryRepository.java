package org.example.springbootrestapi.valueobject;

import org.example.springbootrestapi.entity.Category;
import org.springframework.data.repository.ListCrudRepository;


public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
}
