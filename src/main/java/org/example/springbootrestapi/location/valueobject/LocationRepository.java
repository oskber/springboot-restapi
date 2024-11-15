package org.example.springbootrestapi.location.valueobject;

import org.example.springbootrestapi.entity.LocationEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface LocationRepository extends ListCrudRepository<LocationEntity, Integer> {
    List<LocationEntity> findByStatusTrue();

    List<LocationEntity> findByStatusTrueAndCategoryName(String categoryName);
}
